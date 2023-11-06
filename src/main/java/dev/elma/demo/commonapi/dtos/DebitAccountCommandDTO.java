package dev.elma.demo.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class DebitAccountCommandDTO {
    private String accountId;
    private BigDecimal amount;
    private String currency;
}
