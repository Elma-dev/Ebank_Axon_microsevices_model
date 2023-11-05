package dev.elma.demo.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
public class CreateAccountCommand extends BaseCommand<String>{
    @Getter
    private BigDecimal balance;
    @Getter
    private String currency;

    public CreateAccountCommand(String id,BigDecimal balance,String currency) {
        super(id);
        this.balance=balance;
        this.currency=currency;
    }
}
