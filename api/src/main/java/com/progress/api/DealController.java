package com.progress.api;

import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.request.UpdateDealRequest;
import com.progress.dto.response.DealResponse;
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
    public ResponseEntity<DealResponse> createDeal(@RequestBody CreateDealRequest request) {
        log.info("Create Deal Api With Request : {}", request.toString());

        return new ResponseEntity<>(dealService.createDeal(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DealResponse>> retrieveAllDeals() {
        log.info("Retrieving All Deals Api");

        return new ResponseEntity<>(dealService.retrieveAllDeals(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealResponse> retrieveDealById(@PathVariable Long id) {
        log.info("Retrieve Deal Api With ID : {}", id);

        return new ResponseEntity<>(dealService.retrieveDealById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealResponse> updateDeal(@PathVariable Long id,
                                                   @RequestBody UpdateDealRequest request) {
        log.info("Update Deal Api With Request : {}", request.toString());

        return new ResponseEntity<>(dealService.updateDeal(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteDeal(@PathVariable Long id) {
        log.info("Soft Delete Deal Api With ID : {}", id);

        dealService.softDeleteDeal(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
