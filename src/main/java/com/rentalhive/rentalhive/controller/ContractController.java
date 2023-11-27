package com.rentalhive.rentalhive.controller;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping ("/getContract/{id}")
    public ResponseEntity<String> getContract(@PathVariable int id) {
        return ResponseEntity.ok(contractService.checkEstimateStatusUpdate(id));
    }

    @GetMapping("/getAllApprovedEstimatesForClient/{id}")
    public ResponseEntity<List<Estimate>> getAllApprovedEstimatesForClient(@PathVariable int id) {
        List<Estimate> approvedEstimates = contractService.getAllApprovedEstimatesForClient(id);
        return ResponseEntity.ok(approvedEstimates);
    }

}



