package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Estimate checkEstimateStatusUpdate(int id) {
        Estimate estimate = contractRepository.findById(id);
        if (estimate.getEstimateStatus() == EstimateStatus.Approved) {
            return generateContract(estimate);
        }
        return estimate;
    }

    private Estimate generateContract(Estimate estimate) {
        // Your logic to generate a contract based on the estimate
        return estimate;
    }
}

