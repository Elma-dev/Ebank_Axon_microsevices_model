package dev.elma.demo.queries.services;

import dev.elma.demo.commonapi.enums.TransactionType;
import dev.elma.demo.commonapi.events.AccountCreatedEvent;
import dev.elma.demo.commonapi.events.AccountCreditedEvent;
import dev.elma.demo.commonapi.events.AccountDebitedEvent;
import dev.elma.demo.queries.entities.Account;
import dev.elma.demo.queries.entities.AccountTransaction;
import dev.elma.demo.queries.query.GetAllAcount;
import dev.elma.demo.queries.query.GetOne;
import dev.elma.demo.queries.repositories.AccountRepository;
import dev.elma.demo.queries.repositories.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountEventHandlerServices {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    @EventHandler // create handler listener when some createdEvent saved then this service work
    public void on(AccountCreatedEvent event, EventMessage<AccountCreditedEvent> accountCreditedEventEventMessage){
        log.info("*******************************************************");
        log.info("Account Created Received");
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getBalance().doubleValue());
        account.setStatus(event.getStatus());
        account.setCreatedAt(accountCreditedEventEventMessage.getTimestamp());
        account.setCurrency(event.getCurrency());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage<Account> eventMessage) {
        log.info("*******************************************************");
        log.info("Account Debited Received");
        AccountTransaction accountTransaction = new AccountTransaction();
        Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance()-event.getAmount().doubleValue());
        accountTransaction.setAccount(account);
        accountTransaction.setTimestamp(Date.from(eventMessage.getTimestamp()));
        accountTransaction.setAmount(event.getAmount().doubleValue());
        accountTransaction.setTransactionType(TransactionType.DEBIT);

        accountTransactionRepository.save(accountTransaction);
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAcount getAllAcount){
        log.info("*******************************************************");
        log.info("Get All Query Received");
        //List<Account> all = accountRepository.findAll();
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetOne getOne){
        log.info("*******************************************************");
        log.info("Get One Query Received");
        return accountRepository.findById(getOne.getId()).orElseThrow();
    }


}
