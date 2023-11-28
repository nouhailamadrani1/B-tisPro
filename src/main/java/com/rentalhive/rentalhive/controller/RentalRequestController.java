package com.rentalhive.rentalhive.controller;


import com.rentalhive.rentalhive.model.Files;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.service.impl.FileServiceImpl;
import com.rentalhive.rentalhive.service.impl.RentalRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rentalrequest")
public class RentalRequestController {

    @Autowired
    private RentalRequestServiceImpl rentalRequestServiceImpl;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @GetMapping
    public List<RentalRequest> getAllRentalRequests() {
        return rentalRequestServiceImpl.getAllRentalRequests();
    }

    @GetMapping("/{id}")
    public RentalRequest getRentalRequestById(@PathVariable int id) {
        return rentalRequestServiceImpl.getRentalRequestById(id);
    }


    @PostMapping
    public ResponseEntity<String> addRentalRequest(@RequestBody RentalRequest rentalRequest) {
        try {
            rentalRequestServiceImpl.addRentalRequest(rentalRequest);
            return ResponseEntity.ok("Rental request added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RentalRequest updateRentalRequest(@PathVariable int id,@RequestBody RentalRequest rentalRequest) {
        return rentalRequestServiceImpl.updateRentalRequest(id,rentalRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRentalRequest(@PathVariable int id) {
        rentalRequestServiceImpl.deleteRentalRequest(id);
    }

    @PostMapping("/{rentalRequestId}/files")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int rentalRequestId) {
        try {
            rentalRequestServiceImpl.uploadFile(file, rentalRequestId);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{rentalRequestId}/files")
    public List<Files> getAllFilesByRentalRequestId(@PathVariable int rentalRequestId) {
        return fileServiceImpl.getAllByRentalRequestId(rentalRequestId);
    }




}
