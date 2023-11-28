package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Estimate;

import java.util.List;

public interface ContractServiceInterface {

    String checkEstimateStatusUpdate(int id);

    List<Estimate> getAllApprovedEstimatesForClient(int clientId);

    String archiveContract(int id);

    List<Estimate> getAllNonArchivedEstimatesForClient(int clientId);
}
