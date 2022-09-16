package com.minhub.homebanking;

import com.minhub.homebanking.models.*;

import com.minhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);


	}

	@Bean
	public CommandLineRunner initData(ClientRepositories clientRepository, AccountRepositories accountRepository, TransactionRepositories transactionRepositories, LoanRepositories loanRepositories, ClientLoanRepositories clientLoanRepositories, CardRepositories cardRepositories) {
		return (args) -> {
			// PRESTAMOS //
			Loan hipotecario = new Loan("Mortgage", 5000000, Arrays.asList(12,24,36,48,60));
			Loan personal = new Loan("Personal", 1000000, Arrays.asList(6,12,24));
			Loan automotriz = new Loan( "Car", 3000000, Arrays.asList(6,12,24,36));
			// PRESTAMOS //
			//TRANSACCIONES Y CUENTAS//
			Transaction transactionMelba = new Transaction(TransactionType.CREDIT, 3500, "credit Melba", LocalDateTime.now());
			Account accountMelba = new Account("VIN001", LocalDateTime.now(), 0000.00, AccountType.SAVING);

			Transaction transactionMelba3 = new Transaction(TransactionType.DEBIT, -4500, "debit  Melba", LocalDateTime.now());
			Transaction transactionMelba2 = new Transaction(TransactionType.DEBIT, -1500, "debit  Melba", LocalDateTime.now());
			Account accountMelba2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.00, AccountType.SAVING);


			Transaction transactionAdrian5 = new Transaction(TransactionType.CREDIT, 5500, "credit test", LocalDateTime.now());
			Transaction transactionAdrian4 = new Transaction(TransactionType.DEBIT, -6500, "debit test", LocalDateTime.now());
			Transaction transactionAdrian3 = new Transaction(TransactionType.DEBIT, -4000, "debit test", LocalDateTime.now());
			Transaction transactionAdrian2 = new Transaction(TransactionType.CREDIT, 4500, "credit test", LocalDateTime.now());
			Transaction transactionAdrian = new Transaction(TransactionType.DEBIT, -2500, "debit test", LocalDateTime.now());

			Account accountAdrian2 = new Account("CUENTAPRUEBA02", LocalDateTime.now(), 15000, AccountType.SAVING);
			Account accountAdrian = new Account("CUENTAPRUEBA01",LocalDateTime.now(), 5000, AccountType.SAVING);
			//TRANSACCIONES Y CUENTAS//




			// CLIENTES //
			Client clientAdrian2 = new Client("Adrian", "Rojas", "adrianRojas@admin.com.ar",1129404040,passwordEncoder.encode("adrian123"));
			Client clientMelba = new Client("Melba", "Morel", "melbaMorel@hotmail.com", 201318313,passwordEncoder.encode("melba123"));
			// CLIENTES //



			//CLIENTLOANS//
			ClientLoan clientAdrian3 = new ClientLoan(clientAdrian2,personal.getPayments().get(2),100000,personal);
			ClientLoan clientAdrian4 = new ClientLoan(clientAdrian2,automotriz.getPayments().get(3),200000,automotriz);

			ClientLoan clientmelba3 = new ClientLoan(clientMelba,personal.getPayments().get(2),50000,personal);
			ClientLoan clientmelba4 = new ClientLoan(clientMelba,hipotecario.getPayments().get(4),400000,hipotecario);
			//CLIENTLOANS//

			//CARDS//
			Card cardMelbaGold =  new Card(clientMelba,CardType.DEBIT,CardColor.GOLD,"4003 6440 3318 1144",574,LocalDate.now(), LocalDate.now().plusDays(1));
			Card cardMelbaTitanium =  new Card(clientMelba,CardType.CREDIT,CardColor.TITANIUM,"4003 6440 9584 0973",745,LocalDate.now(), LocalDate.now().plusYears(5));
			Card cardAdrianSilver =  new Card(clientAdrian2,CardType.DEBIT,CardColor.SILVER,"4019 9649 2264 5765",832, LocalDate.now(),LocalDate.now().minusMonths(1));

			//CARDS//

			// CLIENTE MELBA ///
			accountMelba.addTransaction(transactionMelba3);
			accountMelba.addTransaction(transactionMelba);
			accountMelba2.addTransaction(transactionMelba2);
			clientMelba.addCards(cardMelbaTitanium);
			clientMelba.addCards(cardMelbaGold);
			clientMelba.addAccounts(accountMelba);
			clientMelba.addAccounts(accountMelba2);
			clientRepository.save(clientMelba);
			cardRepositories.save(cardMelbaTitanium);
			cardRepositories.save(cardMelbaGold);
			accountRepository.save(accountMelba);
			accountRepository.save(accountMelba2);

			transactionRepositories.save(transactionMelba);
			transactionRepositories.save(transactionMelba2);
			transactionRepositories.save(transactionMelba3);
// CLIENTE MELBA ///
// CLIENTE ADRIAN ///
			accountAdrian2.addTransaction(transactionAdrian2);
			accountAdrian2.addTransaction(transactionAdrian);
			accountAdrian.addTransaction(transactionAdrian3);

			clientAdrian2.addCards(cardAdrianSilver);

			clientAdrian2.addAccounts(accountAdrian2);
			clientAdrian2.addAccounts(accountAdrian);

			clientRepository.save(clientAdrian2);

			cardRepositories.save(cardAdrianSilver);


			accountRepository.save(accountAdrian2);
			accountRepository.save(accountAdrian);

			transactionRepositories.save(transactionAdrian);
			transactionRepositories.save(transactionAdrian2);
			transactionRepositories.save(transactionAdrian3);
			transactionRepositories.save(transactionAdrian4);
			transactionRepositories.save(transactionAdrian5);


			loanRepositories.save(hipotecario);
			loanRepositories.save(personal);
			loanRepositories.save(automotriz);

			clientLoanRepositories.save(clientAdrian3);
			clientLoanRepositories.save(clientAdrian4);

			clientLoanRepositories.save(clientmelba3);
			clientLoanRepositories.save(clientmelba4);


// CLIENTE ADRIAN ///


		};
	}
	@Autowired
	public PasswordEncoder passwordEncoder;

}