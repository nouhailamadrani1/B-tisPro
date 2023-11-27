package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.exceptions.InvalidEstimateException;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.model.RentalRequestStatus;
import com.rentalhive.rentalhive.repository.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private RentalRequestService rentalRequestService;

    public List<Estimate> getAllEstimates() {
        return (List<Estimate>) estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimateById(int id) {
        return estimateRepository.findById(id);
    }

    public Estimate addEstimate(Estimate estimate) {
        // Set estimateStatus to Pending
        estimate.setEstimateStatus(EstimateStatus.Pending);

        // Calculate estimatedCost based on rental request start and end dates
        calculateEstimatedCost(estimate);

        return estimateRepository.save(estimate);
    }

    public Estimate updateEstimate(int id, Estimate estimate) {
        estimate.setId(id);
        return estimateRepository.save(estimate);
    }

    public void deleteEstimate(int id) {
        Optional<Estimate> estimate = getEstimateById(id);

        RentalRequest rentalRequest = estimate.get().getRentalRequest();

        rentalRequest.setRentalRequestStatus(RentalRequestStatus.InProgress);

        estimateRepository.deleteById(id);
    }

    private void calculateEstimatedCost(Estimate estimate) {
        RentalRequest rentalRequest = rentalRequestService.getRentalRequestById(estimate.getRentalRequest().getId());

        if (rentalRequest == null || rentalRequest.getStart_date() == null || rentalRequest.getEnd_date() == null) {
            throw new InvalidEstimateException("Cannot calculate estimated cost. Rental request data is missing or invalid.");
        }

        Date startDate = rentalRequest.getStart_date();
        Date endDate = rentalRequest.getEnd_date();


        Double equipmentPrice = rentalRequest.getEquipment().getPrice();

        // Convert Date objects to Instant (representing a point in time)
        Instant startInstant = startDate.toInstant();
        Instant endInstant = endDate.toInstant();

        // Calculate the duration in days
        long days = Duration.between(startInstant, endInstant).toDays();
        Double estimatedCost = days * equipmentPrice;

        estimate.setEstimatedCost(estimatedCost);
    }

}
