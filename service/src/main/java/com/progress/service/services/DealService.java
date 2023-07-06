package com.progress.service.services;



import com.progress.dto.request.CreateDealRequest;
import com.progress.dto.request.UpdateDealRequest;
import com.progress.dto.response.DealResponse;

import java.util.List;

public interface DealService {

    DealResponse createDeal(CreateDealRequest request);

    List<DealResponse> retrieveAllDeals();

    DealResponse retrieveDealById(Long id);

    DealResponse updateDeal(Long id, UpdateDealRequest request);

    void softDeleteDeal(Long id);
}
