package com.rentalhive.rentalhive.controller;

import com.rentalhive.rentalhive.dto.EstimateDTO;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.impl.EstimateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private EstimateServiceImpl estimateServiceImpl;

    @GetMapping
    public List<EstimateDTO> getAllEstimates() {
        return estimateServiceImpl.getAllEstimates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstimateDTO> getEstimateById(@PathVariable int id) {
        return estimateServiceImpl.getEstimateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstimateDTO> addEstimate(@RequestBody EstimateDTO estimateDTO) {
        EstimateDTO addedEstimateDTO = estimateServiceImpl.addEstimate(estimateDTO);
        return new ResponseEntity<>(addedEstimateDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstimateDTO> updateEstimate(@PathVariable int id, @RequestBody EstimateDTO estimateDTO) {
        EstimateDTO updatedEstimateDTO = estimateServiceImpl.updateEstimate(id, estimateDTO);
        return ResponseEntity.ok(updatedEstimateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEstimate(@PathVariable int id) {
        estimateServiceImpl.deleteEstimate(id);
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<EstimateDTO> updateEstimateStatus(
            @PathVariable int id,
            @RequestBody EstimateDTO updatedEstimateDTO,
            @RequestParam int userId) {

        EstimateDTO updatedEstimateResult = estimateServiceImpl.updateEstimateStatus(id, updatedEstimateDTO, userId);
        return ResponseEntity.ok(updatedEstimateResult);
    }


}
