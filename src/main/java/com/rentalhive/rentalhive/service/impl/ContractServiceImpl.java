package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.repository.ContractRepository;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;
import com.rentalhive.rentalhive.service.ContractServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContractServiceImpl implements ContractServiceInterface {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    public String checkEstimateStatusUpdate(int id) {
        Estimate estimate = contractRepository.findById(id);

        if (estimate != null && estimate.getEstimateStatus() == EstimateStatus.Approved) {
            return generateContract(estimate);
        } else {
            return "Estimate not found or not approved";
        }
    }
    public List<Estimate> getAllApprovedEstimatesForClient(int clientId) {
        List<Estimate> approvedEstimates = new ArrayList<>();

        List<RentalRequest> rentalRequests = rentalRequestRepository.findRentalRequestsByClientId(clientId);

        for (RentalRequest rentalRequest : rentalRequests) {
            List<Estimate> estimates = rentalRequest.getEstimates();
            for (Estimate estimate : estimates) {
                if (estimate.getEstimateStatus() == EstimateStatus.Approved) {
                    approvedEstimates.add(estimate);
                }
            }
        }

        return approvedEstimates;
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

    public String archiveContract(int id) {
        Estimate estimate = contractRepository.findById(id);

        if (estimate != null) {
            estimate.setArchived(true);
            contractRepository.save(estimate);
            return "Contrat archivé avec succès.";
        } else {
            return "Contrat non trouvé.";
        }
    }
    public List<Estimate> getAllNonArchivedEstimatesForClient(int clientId) {
        List<Estimate> nonArchivedEstimates = new ArrayList<>();

        List<RentalRequest> rentalRequests = rentalRequestRepository.findRentalRequestsByClientId(clientId);

        for (RentalRequest rentalRequest : rentalRequests) {
            List<Estimate> estimates = rentalRequest.getEstimates();
            for (Estimate estimate : estimates) {
                if (!estimate.isArchived()) {
                    nonArchivedEstimates.add(estimate);
                }
            }
        }

        return nonArchivedEstimates;
    }
}

