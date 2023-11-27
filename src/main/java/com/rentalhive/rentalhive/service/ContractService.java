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

    public String checkEstimateStatusUpdate(int id) {
        Estimate estimate = contractRepository.findById(id);

        if (estimate != null && estimate.getEstimateStatus() == EstimateStatus.Approved) {
            return generateContract(estimate);
        } else {
            return "Estimate not found or not approved";
        }
    }

    private String generateContract(Estimate estimate) {
        return "Contract {" +
                "Cost=" + estimate.getEstimatedCost() +
                "client=" + estimate.getRentalRequest().getClient().getName() +
                "email=" + estimate.getRentalRequest().getClient().getEmail() +
                "Number Phone=" + estimate.getRentalRequest().getClient().getNumberPhone() +
                "Start date=" + estimate.getRentalRequest().getStart_date() +
                "End date=" + estimate.getRentalRequest().getEnd_date() +
                "Equipment Name=" + estimate.getRentalRequest().getEquipment().getName() +
                "Equipment Type=" + estimate.getRentalRequest().getEquipment().getEquipmentType() +


                '}';
    }
}

