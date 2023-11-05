package dev.elma.demo.commonapi.exceptions;

public class AccountCommandExceptions extends RuntimeException {
    public AccountCommandExceptions(String negativeBalance) {
        super(negativeBalance);
    }
}
