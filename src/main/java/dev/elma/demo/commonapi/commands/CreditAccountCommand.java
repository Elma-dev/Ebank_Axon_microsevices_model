package dev.elma.demo.commonapi.commands;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class CreditAccountCommand extends BaseCommand<String>{
    private BigDecimal amount;
    private String currency;

    public CreditAccountCommand(String accountId, BigDecimal amount,String currency) {
        super(accountId);
        this.amount=amount;
        this.currency=currency;
    }
}
