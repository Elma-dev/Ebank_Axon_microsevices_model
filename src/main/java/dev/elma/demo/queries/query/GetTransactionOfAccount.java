package dev.elma.demo.queries.query;

import dev.elma.demo.queries.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetTransactionOfAccount {
    private Account account;
}
