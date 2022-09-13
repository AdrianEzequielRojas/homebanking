package com.minhub.homebanking.repositories;

import com.minhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepositories extends JpaRepository <Account, Long> {
   Account findByNumber(String numberFrom);

}
