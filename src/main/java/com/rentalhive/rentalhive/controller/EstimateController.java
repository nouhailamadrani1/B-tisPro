package com.rentalhive.rentalhive.controller;

import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @GetMapping
    public List<Estimate> getAllEstimates() {
        return estimateService.getAllEstimates();
    }

    @GetMapping("/{id}")
    public Optional<Estimate> getEstimateById(@PathVariable int id) {
        return estimateService.getEstimateById(id);
    }

    @PostMapping
    public Estimate addEstimate(@RequestBody Estimate estimate) {
        return estimateService.addEstimate(estimate);
    }

    @PutMapping("/{id}")
    public void updateEstimate(@PathVariable int id, @RequestBody Estimate estimate) {
        estimateService.updateEstimate(id, estimate);
    }

    @DeleteMapping("/{id}")
    public void deleteEstimate(@PathVariable int id) {
        estimateService.deleteEstimate(id);
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Estimate> updateEstimateStatus(
            @PathVariable int id,
            @RequestBody Estimate updatedEstimate,
            @RequestParam int userId) {

        // Rest of your code
        Estimate updatedEstimateResult = estimateService.updateEstimateStatus(id, updatedEstimate, userId);
        return ResponseEntity.ok(updatedEstimateResult);
    }


}
