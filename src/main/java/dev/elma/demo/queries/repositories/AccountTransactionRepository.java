package dev.elma.demo.queries.repositories;

import dev.elma.demo.queries.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {
}
