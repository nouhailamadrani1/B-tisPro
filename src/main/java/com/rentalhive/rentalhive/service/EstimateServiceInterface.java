package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.dto.EstimateDTO;
import com.rentalhive.rentalhive.model.Estimate;

import java.util.List;
import java.util.Optional;

public interface EstimateServiceInterface {
    List<EstimateDTO> getAllEstimates();

    Optional<EstimateDTO> getEstimateById(int id);

    EstimateDTO addEstimate(EstimateDTO estimateDTO);

    EstimateDTO updateEstimate(int id, EstimateDTO updatedEstimateDTO);

    void deleteEstimate(int id);

    EstimateDTO updateEstimateStatus(int id, EstimateDTO updatedEstimateDTO, int userId);
}
