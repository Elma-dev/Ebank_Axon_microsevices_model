package dev.elma.demo.commands.controllers;

import dev.elma.demo.commonapi.dtos.CreateAccountCommand;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/account/command")
@AllArgsConstructor
public class AccountCommandController {
    private final CommandGateway commandGateway;    //private final CommandGateway commandGateway;
    @PostMapping("/save")
    public CompletableFuture<String> saveAccountCommand(@RequestBody CreateAccountCommand accountCommand){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                accountCommand.getBalance(),
                accountCommand.getCurrency()
        ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
