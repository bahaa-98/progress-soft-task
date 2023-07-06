package com.progress.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.progress.dto.ResponseDto;
import com.progress.repository.entities.enums.CurrencyCodes;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealResponse implements ResponseDto {

    private Long id;

    private Boolean isActive;

    private LocalDateTime creationTimeStamp;

    private CurrencyCodes fromCurrency;

    private CurrencyCodes toCurrency;

    private BigDecimal amount;
}
