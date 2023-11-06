package dev.elma.demo.commands.controllers;

import dev.elma.demo.commonapi.commands.CreateAccountCommand;
import dev.elma.demo.commonapi.commands.CreditAccountCommand;
import dev.elma.demo.commonapi.commands.DebitAccountCommand;
import dev.elma.demo.commonapi.dtos.CreateAccountCommandDTO;
import dev.elma.demo.commonapi.dtos.CreditAccountCommandDTO;
import dev.elma.demo.commonapi.dtos.DebitAccountCommandDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/account/command")
@AllArgsConstructor
public class AccountCommandController {
    private final CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping("/save")
    public CompletableFuture<String> saveAccountCommand(@RequestBody CreateAccountCommandDTO accountCommand){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                accountCommand.getBalance(),
                accountCommand.getCurrency()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitFromAccount(@RequestBody DebitAccountCommandDTO debitAccountCommandDTO){
        return commandGateway.send(new DebitAccountCommand(
                debitAccountCommandDTO.getAccountId(),
                debitAccountCommandDTO.getAmount(),
                debitAccountCommandDTO.getCurrency()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> debitFromAccount(@RequestBody CreditAccountCommandDTO creditAccountCommandDTO){
        return commandGateway.send(new CreditAccountCommand(
                creditAccountCommandDTO.getAccountId(),
                creditAccountCommandDTO.getAmount(),
                creditAccountCommandDTO.getCurrency()
        ));
    }
    @GetMapping("/events/{id}")
    public Stream showEvent(@PathVariable String id){
        return eventStore.readEvents(id).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
