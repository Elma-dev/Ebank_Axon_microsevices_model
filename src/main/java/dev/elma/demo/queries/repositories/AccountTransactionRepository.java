package dev.elma.demo.queries.repositories;

import dev.elma.demo.queries.entities.Account;
import dev.elma.demo.queries.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {
    public List<AccountTransaction> findAccountTransactionByAccount(Account account);
}
