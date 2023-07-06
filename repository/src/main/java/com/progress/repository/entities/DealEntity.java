package com.progress.repository.entities;

import com.progress.repository.entities.enums.CurrencyCodes;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "creation_timestamp", nullable = false)
    private LocalDateTime creationTimeStamp;

    @Column(name = "update_timestamp", nullable = false)
    private LocalDateTime updateTimeStamp;

    @Column(name = "from_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCodes fromCurrency;

    @Column(name = "to_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCodes toCurrency;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
