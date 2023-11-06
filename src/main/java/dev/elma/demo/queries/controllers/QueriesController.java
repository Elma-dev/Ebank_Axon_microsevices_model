package dev.elma.demo.queries.controllers;

import dev.elma.demo.queries.entities.Account;
import dev.elma.demo.queries.query.GetAllAcount;
import dev.elma.demo.queries.query.GetOne;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/queries")
@AllArgsConstructor
public class QueriesController {
    private QueryGateway queryGateway; // queryGateway is required to consume queries

    @GetMapping("/all")
    public List<Account> allAccounts(){
        //publish the query in query bus
        return queryGateway.query(new GetAllAcount(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }
    @GetMapping("/{id}")
    public CompletableFuture<Account> getAccount(@PathVariable String id){
        return queryGateway.query(new GetOne(id),ResponseTypes.instanceOf(Account.class));
    }

}
