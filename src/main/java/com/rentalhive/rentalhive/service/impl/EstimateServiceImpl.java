package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.dto.EstimateDTO;
import com.rentalhive.rentalhive.exceptions.InvalidEstimateException;
import com.rentalhive.rentalhive.model.*;
import com.rentalhive.rentalhive.repository.EstimateRepository;
import com.rentalhive.rentalhive.service.EstimateServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstimateServiceImpl implements EstimateServiceInterface {

    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private RentalRequestServiceImpl rentalRequestService;

    @Autowired
    private UserServiceImpl userService;

    public EstimateDTO convertToDTO(Estimate estimate) {
        return new EstimateDTO(
                estimate.getId(),
                estimate.getEstimatedCost(),
                estimate.getEstimateStatus(),
                estimate.getRentalRequest(),
                estimate.getAdmin(),
                estimate.isArchived()
        );
    }

    public List<EstimateDTO> getAllEstimates() {
        List<Estimate> estimates = (List<Estimate>) estimateRepository.findAll();
        return estimates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EstimateDTO> getEstimateById(int id) {
        return estimateRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EstimateDTO addEstimate(EstimateDTO estimateDTO) {
        validateAdminRole(estimateDTO.getAdmin().getId());

        Estimate estimate = new Estimate();
        estimate.setEstimateStatus(EstimateStatus.Pending);

        calculateEstimatedCost(estimateDTO);

        estimate.setId(estimateDTO.getId());
        estimate.setEstimatedCost(estimateDTO.getEstimatedCost());
        estimate.setRentalRequest(estimateDTO.getRentalRequest());
        estimate.setAdmin(estimateDTO.getAdmin());
        estimate.setArchived(estimateDTO.isArchived());

        estimateRepository.save(estimate);

        return estimateDTO;
    }

    public EstimateDTO updateEstimate(int id, EstimateDTO updatedEstimateDTO) {
        Estimate existingEstimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        validateAdminRole(updatedEstimateDTO.getAdmin().getId());
        validateEstimatedCost(updatedEstimateDTO);

        existingEstimate.setId(id);
        existingEstimate.setEstimatedCost(updatedEstimateDTO.getEstimatedCost());
        existingEstimate.setRentalRequest(updatedEstimateDTO.getRentalRequest());
        existingEstimate.setAdmin(updatedEstimateDTO.getAdmin());
        existingEstimate.setArchived(updatedEstimateDTO.isArchived());

        estimateRepository.save(existingEstimate);

        return updatedEstimateDTO;
    }

    public void deleteEstimate(int id) {
        Optional<EstimateDTO> estimate = getEstimateById(id);

        RentalRequest rentalRequest = estimate.get().getRentalRequest();

        rentalRequest.setRentalRequestStatus(RentalRequestStatus.InProgress);

        estimateRepository.deleteById(id);
    }

    private void calculateEstimatedCost(EstimateDTO estimate) {
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

    private void validateEstimatedCost(EstimateDTO estimate) {

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

    public EstimateDTO updateEstimateStatus(int id, EstimateDTO updatedEstimateDTO, int userId) {
        Estimate existingEstimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estimate not found with ID: " + id));

        // Validate that the user making the update is the same as the user who created the RentalRequest
        validateUserForEstimateUpdate(userId, existingEstimate);

        existingEstimate.setEstimateStatus(updatedEstimateDTO.getEstimateStatus());

        estimateRepository.save(existingEstimate);

        return updatedEstimateDTO;
    }


    private void validateUserForEstimateUpdate(int userId, Estimate estimate) {
        RentalRequest rentalRequest = estimate.getRentalRequest();

        if (rentalRequest == null || rentalRequest.getClient() == null || rentalRequest.getClient().getId() != userId) {
            throw new InvalidEstimateException("You are not authorized to update the estimate status for this request.");
        }
    }

}
