package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.dto.ContractDTO;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.repository.ContractRepository;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private RentalRequestRepository rentalRequestRepository;

    @InjectMocks
    private ContractServiceImpl contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testGenerateContractPDF_NotApprovedEstimate() {
        // Mock data
        int estimateId = 1;
        Estimate mockEstimate = new Estimate();
        mockEstimate.setEstimateStatus(EstimateStatus.Pending); // Not approved

        when(contractRepository.findById(estimateId)).thenReturn(mockEstimate);

        // Test the method
        byte[] result = contractService.generateContractPDF(estimateId);

        // Assertions
        assertNotNull(result);
        // Add more assertions based on the expected behavior for a non-approved estimate
    }



    @Test
    void testArchiveContract() {
        // Mock data
        int estimateId = 1;
        Estimate mockEstimate = new Estimate();

        when(contractRepository.findById(estimateId)).thenReturn(mockEstimate);

        // Test the method
        String result = contractService.archiveContract(estimateId);

        // Assertions
        assertNotNull(result);
        assertEquals("Contrat archivé avec succès.", result);
        assertTrue(mockEstimate.isArchived());
    }

    @Test
    void testArchiveContract_EstimateNotFound() {
        // Mock data
        int estimateId = 1;

        when(contractRepository.findById(estimateId)).thenReturn(null);

        // Test the method
        String result = contractService.archiveContract(estimateId);

        // Assertions
        assertNotNull(result);
        assertEquals("Contrat non trouvé.", result);
    }


}
