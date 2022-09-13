package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.LoanApplicationDTO;
import com.minhub.homebanking.DTO.LoanDTO;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.*;
import com.minhub.homebanking.services.AccountService;
import com.minhub.homebanking.services.ClientService;
import com.minhub.homebanking.services.LoanService;
import com.minhub.homebanking.services.TransactionService;
import com.minhub.homebanking.services.implementations.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepositories loanRepositories;
    @Autowired
    private TransactionRepositories transactionRepositories;
    @Autowired
    private AccountRepositories accountRepositories;
    @Autowired
    private ClientRepositories clientRepositories;
    @Autowired
    private ClientLoanRepositories clientLoanRepositories;


    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Autowired
    public ClientLoanService clientLoanService;

    @Autowired
    public TransactionService transactionService;


    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.getClientByNumber(loanApplicationDTO.getAccountNumberDestiny());
        Loan loan = loanService.findByName(loanApplicationDTO.getNameLoan());


        if (loanApplicationDTO.getAccountNumberDestiny().isEmpty() || loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (loan == null) {
            return new ResponseEntity<>("loan doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("you are exceeding te max amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("number of payments is not available", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("the account you are trying to get the loan doesn't exist.", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("the account isn't yours", HttpStatus.FORBIDDEN);

        } else {

            ClientLoan clientLoan = new ClientLoan(client, loanApplicationDTO.getPayments(), loanApplicationDTO.getAmount(), loan);


            //         switch (loan.getName()) {
            //             case "Personal Loan":
            //                 switch ((int) clientLoan.getPayment()) {
            //                     case 6:
            //                         clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.20);
            //                        break;
            //                     case 12:
            //                        clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.22);
            //                        break;
            //                    case 24:
            //                        clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.25);
            //                         break;
            //                     case 36:
            //                         clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.30);
            //                         break;
            //                     default:
            //                          break;
            //                 }
            //             case "Automobile Loan":
            //                 switch ((int) clientLoan.getPayment()) {
            //                     case 6:
            //                         clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.20);
            //                          break;
            //                      case 12:
            //                         clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.23);
            //                          break;
            //                     case 24:
            //                         clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.27);
            //                         break;
            //                      case 36:
            //                          clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.32);
            //                          break;
            //                      default:
            //                          break;
            //                   }
            //                case "Mortgage Loan":
            //                   switch ((int) clientLoan.getPayment()) {
            //                      case 12:
            //                           clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.20);
            //                           break;
            //                       case 24:
            //                          clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.25);
            //                          break;
            //                       case 36:
            //                          clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.30);
            //                           break;
            //                        case 48:
        //    clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.35);
            //                            break;
            //                       case 60:
            //                            clientLoan.setAmount(loanApplicationDTO.getAmount() * 1.40);
            //                        default:
//                             break;
//                     }
//                 default:
//                    break;
        //}

        clientLoanService.saveClientLoan(clientLoan);
        Transaction transaction = new Transaction(account, TransactionType.CREDIT, loanApplicationDTO.getAmount(), "Successfully requested loan", LocalDateTime.now());
        transactionService.saveTransactions(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(account);
        return new ResponseEntity<>("loan request successfully", HttpStatus.CREATED);

    }

}


}
