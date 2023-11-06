package dev.elma.demo.commonapi.events;

import dev.elma.demo.commonapi.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AccountCreditedEvent extends BaseEvent<String>{
    private String id;
    private BigDecimal amount;
    private String currency;

    public AccountCreditedEvent(String id, BigDecimal amount,String currency){
        super(id);
        this.amount=amount;
        this.currency=currency;
    }
}
