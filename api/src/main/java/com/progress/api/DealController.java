package com.progress.api;

import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.request.UpdateDealRequest;
import com.progress.dto.response.CODE;
import com.progress.dto.response.DealResponse;
import com.progress.dto.response.Response;
import com.progress.service.services.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deals")
@Slf4j
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping
    public ResponseEntity<Response<DealResponse>> createDeal(@RequestBody CreateDealRequest request) {
        log.info("Create Deal Api With Request : {}", request.toString());

        Response<DealResponse> response = Response.<DealResponse>builder()
                .data(dealService.createDeal(request))
                .code(CODE.CREATED.getId())
                .message(CODE.CREATED.name())
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<DealResponse>>> retrieveAllDeals() {
        log.info("Retrieving All Deals Api");

        Response<List<DealResponse>> response = Response.<List<DealResponse>>builder()
                .data(dealService.retrieveAllDeals())
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<DealResponse>> retrieveDealById(@PathVariable Long id) {
        log.info("Retrieve Deal Api With ID : {}", id);

        Response<DealResponse> response = Response.<DealResponse>builder()
                .data(dealService.retrieveDealById(id))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<DealResponse>> updateDeal(@PathVariable Long id,
                                                   @RequestBody UpdateDealRequest request) {
        log.info("Update Deal Api With Request : {}", request.toString());

        Response<DealResponse> response = Response.<DealResponse>builder()
                .data(dealService.updateDeal(id, request))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> softDeleteDeal(@PathVariable Long id) {
        log.info("Soft Delete Deal Api With ID : {}", id);

        dealService.softDeleteDeal(id);

        Response<Void> response = Response.<Void>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
