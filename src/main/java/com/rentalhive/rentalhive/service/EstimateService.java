package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.repository.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    public List<Estimate> getAllEstimates() {
        return (List<Estimate>) estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimateById(int id) {
        return estimateRepository.findById(id);
    }

    public Estimate addEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);
    }

    public Estimate updateEstimate(int id, Estimate estimate) {
        estimate.setId(id);
        return estimateRepository.save(estimate);
    }

    public void deleteEstimate(int id) {
        estimateRepository.deleteById(id);
    }
}
