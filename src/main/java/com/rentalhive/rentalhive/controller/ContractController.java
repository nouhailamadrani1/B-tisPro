package com.rentalhive.rentalhive.controller;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/getContract")
    public ResponseEntity<Estimate> getContract(@RequestBody int id) {
        Estimate updatedEstimate = contractService.checkEstimateStatusUpdate(id);
        return ResponseEntity.ok(updatedEstimate);
    }
}



