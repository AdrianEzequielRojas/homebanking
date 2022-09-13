package com.minhub.homebanking.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.minhub.homebanking.DTO.FilteredTransactionDTO;
import com.minhub.homebanking.DTO.PaymentApplicationDTO;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.AccountRepositories;
import com.minhub.homebanking.repositories.ClientRepositories;
import com.minhub.homebanking.repositories.TransactionRepositories;
import com.minhub.homebanking.services.AccountService;
import com.minhub.homebanking.services.ClientService;
import com.minhub.homebanking.services.TransactionService;
import com.minhub.homebanking.services.implementations.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private AccountRepositories accountRepositories;
    @Autowired
    private TransactionRepositories transactionRepositories;
    @Autowired
    private ClientRepositories clientRepositories;

    @Autowired
    public TransactionService transactionService;

    @Autowired
    public AccountService accountService;

    @Autowired
    public ClientService clientService;


    @Autowired
    public CardService cardService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransactions(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String numberFrom, @RequestParam String numberTo,
            Authentication authentication
    ) {
        if (amount < 0 || description.isEmpty() || numberFrom.isEmpty() || numberTo.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (numberFrom.equals(numberTo)) {
            return new ResponseEntity<>("accounts can't be same", HttpStatus.FORBIDDEN);
        }
        if (accountService.getClientByNumber(numberFrom) == null) {
            return new ResponseEntity<>("this account don't exist", HttpStatus.FORBIDDEN);
        }
        if (!clientService.findClientByEmail(authentication.getName()).getAccounts().contains(accountService.getClientByNumber(numberFrom))) {
            return new ResponseEntity<>("this not your Account", HttpStatus.FORBIDDEN);
        }
        if (accountService.getClientByNumber(numberTo) == null) {
            return new ResponseEntity<>("this accounts not exist", HttpStatus.FORBIDDEN);
        }
        if (amount > accountService.getClientByNumber(numberFrom).getBalance()) {
            return new ResponseEntity<>("balance enogh", HttpStatus.FORBIDDEN);
        }

        Account accountFrom = accountRepositories.findByNumber(numberFrom);

        Transaction transactionDebit = new Transaction(accountFrom, TransactionType.DEBIT, (int) (amount)*-1, description, LocalDateTime.now());
        transactionService.saveTransactions(transactionDebit);


        Account accountTo = accountRepositories.findByNumber(numberTo);
        Transaction transactionCredit = new Transaction(accountTo, TransactionType.CREDIT, (int) amount, description, LocalDateTime.now());
        transactionService.saveTransactions(transactionCredit);

        accountFrom.setBalance(accountFrom.getBalance()-amount);
        accountTo.setBalance(accountTo.getBalance()+amount);


        accountRepositories.save(accountFrom);
        accountService.saveAccount(accountTo);

        return new ResponseEntity<>("transaction successfully",HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("/transactions/payment")
    public ResponseEntity<Object> paymentsApp (
            @RequestBody PaymentApplicationDTO paymentApplicationDTO, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.getClientByNumber(paymentApplicationDTO.getAccountNumber());
        Card card = cardService.getCardByNumber(paymentApplicationDTO.getCardNumber());


        if(!card.isCardActive()){
            return new ResponseEntity<>("This card was disable, try another ", HttpStatus.FORBIDDEN);
        }
        if(card.getThruDate().isBefore(LocalDateTime.now().toLocalDate())){
            return  new ResponseEntity<>("This card was expires", HttpStatus.FORBIDDEN);
        }
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("This card isn't your", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance()< paymentApplicationDTO.getAmount()){
            return new ResponseEntity<>("You don't have balance", HttpStatus.FORBIDDEN);
        }
        if(!account.isAccountActive()) {
            return new ResponseEntity<>("This account was disable, try another ", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().contains(card.getCvv()!=paymentApplicationDTO.getCvv())){
            return new ResponseEntity<>("cvv invalid ", HttpStatus.FORBIDDEN);
        }
        Transaction transactionPay = new Transaction(account,TransactionType.DEBIT,- paymentApplicationDTO.getAmount(), paymentApplicationDTO.getDescription(), LocalDateTime.now());
        transactionService.saveTransactions(transactionPay);
        account.setBalance(account.getBalance()-paymentApplicationDTO.getAmount());
        accountService.saveAccount(account);
        return new ResponseEntity<>("Payment success",HttpStatus.CREATED);
    }

    @PostMapping("/transactions/downloads")
    public ResponseEntity <Object> getFilteredTransaction(
            @RequestBody FilteredTransactionDTO filteredTransactionDTO, Authentication authentication) throws DocumentException, FileNotFoundException {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.getClientByNumber(filteredTransactionDTO.getAccountNumber());
        if(filteredTransactionDTO.accountNumber.isEmpty() || filteredTransactionDTO.fromDate ==null || filteredTransactionDTO.thruDate==null){
            return  new ResponseEntity<>("Please fill all the form fields", HttpStatus.FORBIDDEN);
        }
        if(!account.isAccountActive()){
            return new ResponseEntity<>("This account was disable, try another ", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("This account isn't yours", HttpStatus.FORBIDDEN);
        }
        if(account.getTransactions()==null){
            return new ResponseEntity<>("this accounts not have any transaction", HttpStatus.FORBIDDEN);
        }
        Set <Transaction> transactions = transactionService.filterTransactionsWithDate(filteredTransactionDTO.fromDate, filteredTransactionDTO.thruDate,account);
        createTable(transactions);
        return new ResponseEntity<>("Done! check yours downloads", HttpStatus.CREATED);
    }
    public  void createTable(Set<Transaction> transactionArray) throws FileNotFoundException, DocumentException{
        var document = new Document();
        String route = System.getProperty("user.home");
        PdfWriter.getInstance(document,new FileOutputStream(route+"/Descargas/Transactions_report.pdf"));

        document.open();

        var bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        var paragraph = new Paragraph("Transactions that occurred between the seleted dates");

        var table = new PdfPTable(4);
        Stream.of("Amount","Description","Date", "Type").forEach(table::addCell);

        transactionArray
                .forEach(transaction -> {
                    table.addCell("$"+transaction.getAmount());
                    table.addCell(transaction.getDescription());
                    table.addCell(transaction.getDate().toString());
                    table.addCell(transaction.getType().toString());
                });
        paragraph.add(table);
        document.add(paragraph);
        document.close();
    }
}
