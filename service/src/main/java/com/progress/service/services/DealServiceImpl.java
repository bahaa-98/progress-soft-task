package com.progress.service.services;


import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.request.UpdateDealRequest;
import com.progress.dto.response.DealResponse;
import com.progress.repository.entities.DealEntity;
import com.progress.repository.repositories.DealRepository;
import com.progress.service.exceptions.NotFoundException;
import com.progress.service.mappers.DealMapper;
import com.progress.service.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.progress.service.validator.CompositeValidator.joinViolations;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final DealMapper dealMapper;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
        dealMapper = new DealMapper();
    }

    @Override
    public DealResponse createDeal(CreateDealRequest request) {
        log.info("Creating Deal with request : {}", request.toString());

        validateCreateDealRequest(request);

        DealEntity dealEntity = dealRepository.save(dealMapper.mapRequestToEntity(request));

        return dealMapper.mapEntityToResponse(dealEntity);
    }

    private void validateCreateDealRequest(CreateDealRequest request) {
        log.info("Validating Create Deal Request : {}", request.toString());

        List<String> violations = new CompositeValidator<CreateDealRequest, String>()
                .addValidator(p -> isNull(p.getToCurrency()), "toCurrency cannot be null")
                .addValidator(p -> isNull(p.getFromCurrency()), "fromCurrency cannot be null")
                .addValidator(p -> isNull(p.getAmount()), "amount cannot be null")
                .addValidator(p -> nonNull(p.getAmount()) && p.getAmount().intValue() <= 0, "amount cannot be less or equal to 0")
                .addValidator(p -> nonNull(p.getToCurrency()) && nonNull(p.getFromCurrency()) && p.getFromCurrency().equals(p.getToCurrency()),
                        "fromCurrency shouldn't be equal to toCurrency")
                .validate(request);

        joinViolations(violations);
    }

    @Override
    public List<DealResponse> retrieveAllDeals() {
        log.info("Retrieving All Active Deals From DB");

        return retrieveAllActiveDealsFromDB().stream()
                .map(dealMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private List<DealEntity> retrieveAllActiveDealsFromDB() {
        return dealRepository.findAll(Example.of(DealEntity.builder()
                .isActive(true)
                .build()));
    }

    @Override
    public DealResponse retrieveDealById(Long id) {
        log.info("Retrieving Active Deal From DB With ID : {}", id);

        return dealMapper.mapEntityToResponse(retrieveActiveDealByIdFromDB(id));
    }

    private DealEntity retrieveActiveDealByIdFromDB(Long id) {
        return dealRepository.findOne(Example.of(DealEntity.builder()
                        .isActive(true)
                        .id(id)
                        .build()))
                .orElseThrow(() -> new NotFoundException("No Active Deal Was Found With ID :" + id));
    }

    @Override
    public DealResponse updateDeal(Long id, UpdateDealRequest request) {
        log.info("Updating Deal With ID : {} With Request : {}", id, request.toString());

        DealEntity dealEntity = retrieveActiveDealByIdFromDB(id);

        validateUpdateDealRequest(request);

        DealEntity updatedDealEntity = updateEntityValues(dealEntity, request);

        return dealMapper.mapEntityToResponse(dealRepository.save(updatedDealEntity));
    }

    private DealEntity updateEntityValues(DealEntity dealEntity, UpdateDealRequest request) {
        log.info("Updating Deal Entity Values to New Request : {}", request.toString());

        return dealEntity.toBuilder()
                .fromCurrency(request.getFromCurrency())
                .toCurrency(request.getToCurrency())
                .amount(request.getAmount())
                .updateTimeStamp(LocalDateTime.now())
                .build();
    }

    private void validateUpdateDealRequest(UpdateDealRequest request) {
        log.info("Validating Update Deal Request : {}", request.toString());

        List<String> violations = new CompositeValidator<UpdateDealRequest, String>()
                .addValidator(p -> isNull(p.getToCurrency()), "toCurrency cannot be null")
                .addValidator(p -> isNull(p.getFromCurrency()), "fromCurrency cannot be null")
                .addValidator(p -> isNull(p.getAmount()), "amount cannot be null")
                .addValidator(p -> nonNull(p.getAmount()) && p.getAmount().intValue() <= 0, "amount cannot be less or equal to 0")
                .addValidator(p -> nonNull(p.getToCurrency()) && nonNull(p.getFromCurrency()) && p.getFromCurrency().equals(p.getToCurrency()),
                        "fromCurrency shouldn't be equal to toCurrency")
                .validate(request);

        joinViolations(violations);
    }

    @Override
    public void softDeleteDeal(Long id) {
        log.info("Soft Delete Deal Entity With ID : {}", id);

        DealEntity dealEntity = retrieveActiveDealByIdFromDB(id);

        dealEntity.setIsActive(false);
        dealEntity.setUpdateTimeStamp(LocalDateTime.now());

        dealRepository.save(dealEntity);
    }
}
