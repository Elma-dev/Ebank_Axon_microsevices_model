package dev.elma.demo.queries.repositories;

import dev.elma.demo.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
