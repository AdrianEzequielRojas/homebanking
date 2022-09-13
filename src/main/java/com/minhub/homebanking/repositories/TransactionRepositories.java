package com.minhub.homebanking.repositories;

import com.minhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Set;

@RepositoryRestResource
public interface TransactionRepositories extends JpaRepository<Transaction, Long> {
    public Set<Transaction> findByDateBetween(LocalDateTime from, LocalDateTime to);
}
