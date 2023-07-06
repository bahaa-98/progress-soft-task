package com.progress.service.services;

import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.request.UpdateDealRequest;
import com.progress.dto.response.DealResponse;
import com.progress.repository.entities.DealEntity;
import com.progress.repository.entities.enums.CurrencyCodes;
import com.progress.repository.repositories.DealRepository;
import com.progress.service.exceptions.BadRequestException;
import com.progress.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    private DealServiceImpl dealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dealService = new DealServiceImpl(dealRepository);
    }

    @Test
    void validateCreateDealRequest_ToCurrencyIsNull_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().fromCurrency(CurrencyCodes.USD)
                .amount(BigDecimal.valueOf(100)).build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void validateCreateDealRequest_FromCurrencyIsNull_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .amount(BigDecimal.valueOf(100)).build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void validateCreateDealRequest_AmountIsNull_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .fromCurrency(CurrencyCodes.EUR).build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void validateCreateDealRequest_AmountIsEqualToZero_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(0))
                .build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void validateCreateDealRequest_AmountIsLessThanZero_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(-200))
                .build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void validateCreateDealRequest_FromCurrencyEqualsToCurrency_ThrowsException() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .fromCurrency(CurrencyCodes.USD).amount(BigDecimal.valueOf(200)).build();

        assertThrows(BadRequestException.class, () -> dealService.createDeal(request));
    }

    @Test
    void CreateDealRequest_ValidRequest_ReturnsResponse() {
        CreateDealRequest request = CreateDealRequest.builder().toCurrency(CurrencyCodes.USD)
                .fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(200))
                .build();

        LocalDateTime now = LocalDateTime.now();

        when(dealRepository.save(any(DealEntity.class))).thenReturn(DealEntity.builder()
                .id(1L)
                .isActive(true)
                .creationTimeStamp(now)
                .updateTimeStamp(now)
                .toCurrency(request.getToCurrency())
                .fromCurrency(request.getFromCurrency())
                .amount(request.getAmount())
                .build());

        DealResponse dealResponse = dealService.createDeal(request);

        assertEquals(1L, dealResponse.getId());
        assertTrue(dealResponse.getIsActive());
        assertEquals(now, dealResponse.getCreationTimeStamp());
        assertEquals(CurrencyCodes.EUR, dealResponse.getFromCurrency());
        assertEquals(CurrencyCodes.USD, dealResponse.getToCurrency());
        assertEquals(BigDecimal.valueOf(200), dealResponse.getAmount());
    }

    @Test
    void retrieveAllDeals_NoDeals_ReturnsEmptyList() {
        when(dealRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<DealResponse> deals = dealService.retrieveAllDeals();

        assertNotNull(deals);
        assertEquals(0, deals.size());
    }

    @Test
    void retrieveAllDeals_OneDeal_ReturnsListOfOne() {
        when(dealRepository.findAll(any(Example.class))).thenReturn(Collections.singletonList(DealEntity.builder()
                .build()));

        List<DealResponse> deals = dealService.retrieveAllDeals();

        assertNotNull(deals);
        assertEquals(1, deals.size());
    }

    @Test
    void retrieveDealById_DealExists_ReturnsDealResponse() {
        DealEntity dealEntity = DealEntity.builder().id(1L).isActive(true)
                .toCurrency(CurrencyCodes.USD).fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(100))
                .build();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        DealResponse dealResponse = dealService.retrieveDealById(1L);

        assertNotNull(dealResponse);
        assertEquals(1L, dealResponse.getId());
        assertTrue(dealResponse.getIsActive());
        assertEquals(CurrencyCodes.EUR, dealResponse.getFromCurrency());
        assertEquals(CurrencyCodes.USD, dealResponse.getToCurrency());
        assertEquals(BigDecimal.valueOf(100), dealResponse.getAmount());
    }

    @Test
    void retrieveDealById_DealNotExists_ThrowsException() {

        when(dealRepository.findOne(any(Example.class))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> dealService.retrieveDealById(-10L));
    }

    @Test
    void validateUpdateDealRequest_ToCurrencyIsNull_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().fromCurrency(CurrencyCodes.JOD)
                .amount(BigDecimal.valueOf(100)).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void validateUpdateDealRequest_FromCurrencyIsNull_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().toCurrency(CurrencyCodes.JOD)
                .amount(BigDecimal.valueOf(100)).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void validateUpdateDealRequest_AmountIsNull_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().fromCurrency(CurrencyCodes.JOD)
                .toCurrency(CurrencyCodes.USD).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void validateUpdateDealRequest_AmountIsEqualToZero_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().fromCurrency(CurrencyCodes.JOD)
                .toCurrency(CurrencyCodes.USD).amount(BigDecimal.valueOf(0)).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void validateUpdateDealRequest_AmountIsLessThanZero_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().fromCurrency(CurrencyCodes.JOD)
                .toCurrency(CurrencyCodes.USD).amount(BigDecimal.valueOf(-20)).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void validateUpdateDealRequest_FromCurrencyEqualsToCurrency_ThrowsException() {
        DealEntity dealEntity = new DealEntity();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().fromCurrency(CurrencyCodes.JOD)
                .toCurrency(CurrencyCodes.JOD).amount(BigDecimal.valueOf(100)).build();

        assertThrows(BadRequestException.class, () -> dealService.updateDeal(1L, request));
    }

    @Test
    void updateDealRequest_ValidRequest_ReturnsResponse() {
        DealEntity dealEntity = DealEntity.builder().id(1L).isActive(true)
                .toCurrency(CurrencyCodes.USD).fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(100))
                .build();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        UpdateDealRequest request = UpdateDealRequest.builder().toCurrency(CurrencyCodes.JOD)
                .fromCurrency(CurrencyCodes.AED).amount(BigDecimal.valueOf(200))
                .build();

        when(dealRepository.save(any(DealEntity.class))).thenReturn(DealEntity.builder().id(1L).isActive(true)
                .toCurrency(CurrencyCodes.JOD).fromCurrency(CurrencyCodes.AED).amount(BigDecimal.valueOf(200))
                .build());

        DealResponse dealResponse = dealService.updateDeal(1L, request);

        assertEquals(1L, dealResponse.getId());
        assertTrue( dealResponse.getIsActive());
        assertEquals(CurrencyCodes.AED, dealResponse.getFromCurrency());
        assertEquals(CurrencyCodes.JOD, dealResponse.getToCurrency());
        assertEquals(BigDecimal.valueOf(200), dealResponse.getAmount());
    }

    @Test
    void softDelete_DealNotExists_ThrowsException() {

        when(dealRepository.findOne(any(Example.class))).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> dealService.retrieveDealById(-10L));
    }

    @Test
    void softDelete_ValidRequest_UpdateValues() {
        DealEntity dealEntity = DealEntity.builder().id(1L).isActive(true)
                .toCurrency(CurrencyCodes.USD).fromCurrency(CurrencyCodes.EUR).amount(BigDecimal.valueOf(100))
                .build();

        when(dealRepository.findOne(any(Example.class))).thenReturn(Optional.of(dealEntity));

        dealService.softDeleteDeal(1L);

        verify(dealRepository).save(dealEntity);

        assertFalse(dealEntity.getIsActive());
    }
}