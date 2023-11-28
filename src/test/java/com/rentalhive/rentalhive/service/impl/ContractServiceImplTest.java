package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.repository.ContractRepository;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;
import com.rentalhive.rentalhive.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testCheckEstimateStatusUpdateEstimateNotFound() {
        // Arrange
        int estimateId = 1;

        when(contractRepository.findById(estimateId)).thenReturn(null);

        // Act
        String result = contractService.checkEstimateStatusUpdate(estimateId);

        // Assert
        assertEquals("Estimate not found or not approved", result);
    }


}
