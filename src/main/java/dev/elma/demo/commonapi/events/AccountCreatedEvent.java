package dev.elma.demo.commonapi.events;

import dev.elma.demo.commonapi.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private BigDecimal balance;
    @Getter private String currency;
    @Getter private AccountStatus status;

    public AccountCreatedEvent(String id, BigDecimal balance, String currency, AccountStatus status){
        super(id);
        this.balance=balance;
        this.currency=currency;
        this.status=status;
    }
}
