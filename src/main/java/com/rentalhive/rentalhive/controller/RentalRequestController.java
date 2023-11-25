package com.rentalhive.rentalhive.controller;


import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.service.RentalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rentalrequest")
public class RentalRequestController {

    @Autowired
    private RentalRequestService rentalRequestService;

    @GetMapping
    public List<RentalRequest> getAllRentalRequests() {
        return rentalRequestService.getAllRentalRequests();
    }

    @GetMapping("/{id}")
    public RentalRequest getRentalRequestById(@PathVariable int id) {
        return rentalRequestService.getRentalRequestById(id);
    }


    @PostMapping
    public ResponseEntity<String> addRentalRequest(@RequestBody RentalRequest rentalRequest) {
        try {
            rentalRequestService.addRentalRequest(rentalRequest);
            return ResponseEntity.ok("Rental request added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RentalRequest updateRentalRequest(@PathVariable int id,@RequestBody RentalRequest rentalRequest) {
        return rentalRequestService.updateRentalRequest(id,rentalRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRentalRequest(@PathVariable int id) {
        rentalRequestService.deleteRentalRequest(id);
    }

    @PostMapping("/files")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            rentalRequestService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
