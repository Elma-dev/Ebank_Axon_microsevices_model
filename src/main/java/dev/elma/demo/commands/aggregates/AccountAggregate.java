package dev.elma.demo.commands.aggregates;

import dev.elma.demo.commonapi.dtos.CreateAccountCommand;
import dev.elma.demo.commonapi.enums.AccountStatus;
import dev.elma.demo.commonapi.exceptions.AccountCommandExceptions;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;

import java.math.BigDecimal;

@NoArgsConstructor // provide by axon
public class AccountAggregate {
    private String id;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    @CommandHandler

    public AccountAggregate(CreateAccountCommand accountCommand) {
        //Decision Functions Here
        if(accountCommand.getBalance().doubleValue()<=0) throw new AccountCommandExceptions("Negative Balance");
        AggregateLifecycle.apply() // save events
    }
}
