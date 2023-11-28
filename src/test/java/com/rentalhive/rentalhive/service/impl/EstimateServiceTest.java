package com.rentalhive.rentalhive.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.repository.EstimateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EstimateServiceTest {

    @Mock
    private EstimateRepository estimateRepository;

    @InjectMocks
    private EstimateService estimateService;

    @Test
    void testGetAllEstimates() {

        Estimate estimate1 = new Estimate();
        Estimate estimate2 = new Estimate();
        List<Estimate> expectedEstimates = Arrays.asList(estimate1, estimate2);

        Mockito.when(estimateRepository.findAll()).thenReturn(expectedEstimates);

        List<Estimate> actualEstimates = estimateService.getAllEstimates();

        assertEquals(expectedEstimates.size(), actualEstimates.size());
        assertTrue(actualEstimates.containsAll(expectedEstimates));
    }


    @Test
    void testGetEstimateById() {
        int estimateId = 19;
        Estimate expectedEstimate = new Estimate();

        Mockito.when(estimateRepository.findById(estimateId)).thenReturn(Optional.of(expectedEstimate));

        Optional<Estimate> actualEstimate = estimateService.getEstimateById(estimateId);

        assertTrue(actualEstimate.isPresent());
        assertEquals(expectedEstimate, actualEstimate.get());
    }

    @Test
    void testGetEstimateByIdNotFound() {
        int nonExistentId = 999;

        Mockito.when(estimateRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<Estimate> actualEstimate = estimateService.getEstimateById(nonExistentId);

        assertTrue(actualEstimate.isEmpty());
    }
    
}
