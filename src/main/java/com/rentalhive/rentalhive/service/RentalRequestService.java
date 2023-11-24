package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.RentalRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;

import java.util.List;

@Service
public class RentalRequestService {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    private List<RentalRequest> getAllRentalRequest() {
        return rentalRequestRepository.findAll();
    }

    private RentalRequest getRentalRequestById(int id) {
        return rentalRequestRepository.findById(id).orElse(null);
    }

    private RentalRequest addRentalRequest(RentalRequest rentalRequest) {
        return rentalRequestRepository.save(rentalRequest);
    }

    private RentalRequest updateRentalRequest(int id,RentalRequest rentalRequest) {
        rentalRequest.setId(id);
        return rentalRequestRepository.save(rentalRequest);
    }

    private void deleteRentalRequest(int id) {
        rentalRequestRepository.deleteById(id);
    }
}
