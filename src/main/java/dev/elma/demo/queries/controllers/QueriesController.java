package dev.elma.demo.queries.controllers;

import dev.elma.demo.queries.entities.Account;
import dev.elma.demo.queries.query.controllers.GetAllAcount;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
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
    public CompletableFuture<List<Account>> allAccounts(){
        return queryGateway.query(new GetAllAcount(), ResponseTypes.multipleInstancesOf(Account.class));
    }

}
