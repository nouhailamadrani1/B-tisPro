package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.exceptions.InvalidEstimateException;
import com.rentalhive.rentalhive.model.*;
import com.rentalhive.rentalhive.repository.EstimateRepository;
import com.rentalhive.rentalhive.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private RentalRequestService rentalRequestService;

    @Autowired
    private UserServiceImpl userService;


    public List<Estimate> getAllEstimates() {
        return (List<Estimate>) estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimateById(int id) {
        return estimateRepository.findById(id);
    }

    public Estimate addEstimate(Estimate estimate) {

        validateAdminRole(estimate.getAdmin().getId());
        estimate.setEstimateStatus(EstimateStatus.Pending);

        // Calculate estimatedCost based on rental request start and end dates
        calculateEstimatedCost(estimate);

        return estimateRepository.save(estimate);
    }

    public void updateEstimate(int id, Estimate estimate) {

        validateAdminRole(estimate.getAdmin().getId());
        validateEstimatedCost(estimate);

        estimate.setId(id);

        estimateRepository.save(estimate);
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

    private void validateEstimatedCost(Estimate estimate) {

        Double estimatedCost = estimate.getEstimatedCost();

        if (estimatedCost == null || estimatedCost < 0 || Double.isNaN(estimatedCost) || Double.isInfinite(estimatedCost)) {
            throw new InvalidEstimateException("Invalid estimated cost. Please provide a valid non-negative double value.");
        }
    }

    private void validateAdminRole(int adminId) {
        User admin = userService.getUserById(adminId)
                .orElseThrow(() -> new InvalidEstimateException("Admin not found with ID: " + adminId));

        if (!admin.getRole().equals(Role.Manager)) {
            throw new InvalidEstimateException("The provided admin does not have the required role 'Manager'.");
        }
    }

    public Estimate updateEstimateStatus(int id, Estimate updatedEstimate, int userId) {
        Estimate existingEstimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        // Validate that the user making the update is the same as the user who created the RentalRequest
        validateUserForEstimateUpdate(userId, existingEstimate);

        // Update the estimate status
        existingEstimate.setEstimateStatus(updatedEstimate.getEstimateStatus());

        // Additional validation or business logic can be added here if needed

        return estimateRepository.save(existingEstimate);
    }

    private void validateUserForEstimateUpdate(int userId, Estimate estimate) {
        RentalRequest rentalRequest = estimate.getRentalRequest();

        if (rentalRequest == null || rentalRequest.getClient() == null || rentalRequest.getClient().getId() != userId) {
            throw new InvalidEstimateException("You are not authorized to update the estimate status for this request.");
        }
    }

}
