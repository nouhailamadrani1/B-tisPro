package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.dto.ContractDTO;
import com.rentalhive.rentalhive.model.Estimate;

import java.io.IOException;
import java.util.List;

import java.util.List;

public interface ContractServiceInterface {

    byte[] generateContractPDF(int id);

    List<ContractDTO> getAllApprovedEstimatesForClient(int clientId);

    String archiveContract(int id);

    List<ContractDTO> getAllNonArchivedEstimatesForClient(int clientId);
}
