package com.progress.service.mappers;

import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.response.DealResponse;
import com.progress.repository.entities.DealEntity;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class DealMapper {

    public DealEntity mapRequestToEntity(CreateDealRequest request) {
        log.info("Mapping Create Deal Request : {} To Entity", request.toString());
        LocalDateTime now = LocalDateTime.now();

        return DealEntity.builder()
                .isActive(true)
                .creationTimeStamp(now)
                .updateTimeStamp(now)
                .fromCurrency(request.getFromCurrency())
                .toCurrency(request.getToCurrency())
                .amount(request.getAmount())
                .build();
    }

    public DealResponse mapEntityToResponse(DealEntity dealEntity) {
        log.info("Mapping Deal Entity to Response");

        return DealResponse.builder()
                .id(dealEntity.getId())
                .isActive(dealEntity.getIsActive())
                .creationTimeStamp(dealEntity.getCreationTimeStamp())
                .fromCurrency(dealEntity.getFromCurrency())
                .toCurrency(dealEntity.getToCurrency())
                .amount(dealEntity.getAmount())
                .build();
    }
}
