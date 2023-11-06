package dev.elma.demo.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CreateAccountCommandDTO {
    private BigDecimal balance;
    private String currency;
}
