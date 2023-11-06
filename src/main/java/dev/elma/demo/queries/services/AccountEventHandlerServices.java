package dev.elma.demo.queries.services;

import dev.elma.demo.commonapi.events.AccountCreatedEvent;
import dev.elma.demo.commonapi.events.AccountCreditedEvent;
import dev.elma.demo.queries.entities.Account;
import dev.elma.demo.queries.query.controllers.GetAllAcount;
import dev.elma.demo.queries.repositories.AccountRepository;
import dev.elma.demo.queries.repositories.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AccountEventHandlerServices {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    @EventHandler // create handler listener when some createdEvent saved then this service work
    public void on(AccountCreatedEvent event, EventMessage<AccountCreditedEvent> accountCreditedEventEventMessage){
        System.out.println("**********Account Repository Received***********");
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getBalance().doubleValue());
        account.setStatus(event.getStatus());
        account.setCreatedAt(accountCreditedEventEventMessage.getTimestamp());
        account.setCurrency(event.getCurrency());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAcount getAllAcount){
        return accountRepository.findAll();
    }
}
