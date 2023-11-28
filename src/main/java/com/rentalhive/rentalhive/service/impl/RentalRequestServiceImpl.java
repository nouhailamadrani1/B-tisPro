package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.service.RentalRequestServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class RentalRequestServiceImpl implements RentalRequestServiceInterface {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    public List<RentalRequest> getAllRentalRequests() {
        return rentalRequestRepository.findAll();
    }

    public RentalRequest getRentalRequestById(int id) {
        return rentalRequestRepository.findById(id).orElse(null);
    }

    public RentalRequest addRentalRequest(RentalRequest rentalRequest) {

        if (rentalRequest.getStart_date().after(rentalRequest.getEnd_date())) {
            throw new RuntimeException("Start date cannot be after end date");
        } else if (rentalRequest.getStart_date().before(new Date())) {
            throw new RuntimeException("Start date cannot be in the past");
        } else if (rentalRequest.getEnd_date().before(new Date())) {
            throw new RuntimeException("End date cannot be in the past");
        } else if (rentalRequest.getStart_date().equals(rentalRequest.getEnd_date())) {
            throw new RuntimeException("Start date cannot be the same as end date");
        } else if (!rentalRequestRepository.isAvailable(rentalRequest.getEquipment().getId(), rentalRequest.getStart_date(), rentalRequest.getEnd_date())) {
            throw new RuntimeException("Equipment is not available for the given dates");
        }

        return rentalRequestRepository.save(rentalRequest);
    }

    public RentalRequest updateRentalRequest(int id,RentalRequest rentalRequest) {
        rentalRequest.setId(id);
        return rentalRequestRepository.save(rentalRequest);
    }

    public void deleteRentalRequest(int id) {
        rentalRequestRepository.deleteById(id);
    }

    public void uploadFile(MultipartFile file, int rentalRequestId) {
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get( "src/main/resources/files/"+ file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        com.rentalhive.rentalhive.model.Files file1 = new com.rentalhive.rentalhive.model.Files();
        file1.setPath("src/main/resources/files/"+ file.getOriginalFilename());
        file1.setRentalRequest(getRentalRequestById(rentalRequestId));

        fileServiceImpl.saveFile(file1);
    }


}
