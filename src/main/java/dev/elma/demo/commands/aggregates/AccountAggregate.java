package dev.elma.demo.commands.aggregates;

import dev.elma.demo.commonapi.commands.CreateAccountCommand;
import dev.elma.demo.commonapi.commands.CreditAccountCommand;
import dev.elma.demo.commonapi.commands.DebitAccountCommand;
import dev.elma.demo.commonapi.dtos.CreditAccountCommandDTO;
import dev.elma.demo.commonapi.dtos.DebitAccountCommandDTO;
import dev.elma.demo.commonapi.enums.AccountStatus;
import dev.elma.demo.commonapi.events.AccountCreatedEvent;
import dev.elma.demo.commonapi.events.AccountCreditedEvent;
import dev.elma.demo.commonapi.events.AccountDebitedEvent;
import dev.elma.demo.commonapi.exceptions.AccountCommandExceptions;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate //axonAggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    protected AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        //Decision Functions Here
        if(command.getBalance().doubleValue()<=0) throw new AccountCommandExceptions("Negative Balance");
        // When all commands are acceptable then we transfer them  to be events and save it in events store
        AccountCreatedEvent event = new AccountCreatedEvent(command.getId(), command.getBalance(), command.getCurrency(), AccountStatus.CREATED);
        AggregateLifecycle.apply(event); // publish events
    }

    //Now we create an event handler whose update the state of our account (application)
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.id=event.getId();
        this.balance=event.getBalance();
        this.currency=event.getCurrency();
        this.status=event.getStatus();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(this.balance.doubleValue()<command.getAmount().doubleValue()) throw new AccountCommandExceptions("Balance insufficient");
        AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent(command.getId().toString(), command.getAmount(), command.getCurrency());
        AggregateLifecycle.apply(accountDebitedEvent);

    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance=BigDecimal.valueOf(this.balance.doubleValue()-event.getAmount().doubleValue());
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount().doubleValue()<=0) throw new AccountCommandExceptions("Negative Amount");
        AggregateLifecycle.apply(
                new AccountCreditedEvent(
                        command.getId(),
                        command.getAmount(),
                        command.getCurrency()
                )
        );

    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance=BigDecimal.valueOf(this.balance.doubleValue()+event.getAmount().doubleValue());
    }
}
