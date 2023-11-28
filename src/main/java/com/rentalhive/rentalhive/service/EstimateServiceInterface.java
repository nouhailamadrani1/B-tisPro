package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Estimate;

import java.util.List;
import java.util.Optional;

public interface EstimateServiceInterface {
    List<Estimate> getAllEstimates();

    Optional<Estimate> getEstimateById(int id);

    Estimate addEstimate(Estimate estimate);

    void updateEstimate(int id, Estimate estimate);

    void deleteEstimate(int id);

    Estimate updateEstimateStatus(int id, Estimate updatedEstimate, int userId);

}
