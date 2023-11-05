package dev.elma.demo.commands.controllers;

import dev.elma.demo.commonapi.dtos.CreateAccountCommand;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/account/command")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;

    @PostMapping("/save")
    public CompletableFuture<String> saveAccountCommand(@RequestBody CreateAccountCommand accountCommand){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                accountCommand.getBalance(),
                accountCommand.getCurrency()
        ));
    }
    @ExceptionHandler(Exception.class)
    Exception exceptionHandler(Exception e){
        return e;
    }

}
