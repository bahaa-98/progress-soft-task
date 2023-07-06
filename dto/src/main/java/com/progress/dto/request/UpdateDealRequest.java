package com.progress.dto.request;

import com.progress.dto.RequestDto;
import com.progress.repository.entities.enums.CurrencyCodes;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateDealRequest implements RequestDto {

    private CurrencyCodes fromCurrency;

    private CurrencyCodes toCurrency;

    private BigDecimal amount;
}
