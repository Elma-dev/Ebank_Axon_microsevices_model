package dev.elma.demo.commonapi.events;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AccountDebitedEvent extends BaseEvent<String>{
    private BigDecimal amount;
    private String currency;

    public AccountDebitedEvent(String id, BigDecimal amount,String currency){
        super(id);
        this.amount=amount;
        this.currency=currency;
    }
}
