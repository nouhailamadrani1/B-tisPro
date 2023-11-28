package com.rentalhive.rentalhive.controller;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.ContractServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractServiceInterface contractService;

    @GetMapping ("/getContract/{id}")
    public ResponseEntity<String> getContract(@PathVariable int id) {
        return ResponseEntity.ok(contractService.checkEstimateStatusUpdate(id));
    }

    @GetMapping("/getAllApprovedEstimatesForClient/{id}")
    public ResponseEntity<List<Estimate>> getAllApprovedEstimatesForClient(@PathVariable int id) {
        List<Estimate> approvedEstimates = contractService.getAllApprovedEstimatesForClient(id);
        return ResponseEntity.ok(approvedEstimates);
    }
    @PutMapping("/archiveContract/{id}")
    public ResponseEntity<String> archiveContract(@PathVariable int id) {
        return ResponseEntity.ok(contractService.archiveContract(id));
    }
    @GetMapping("/getAllNonArchivedEstimatesForClient/{id}")
    public ResponseEntity<List<Estimate>> getAllNonArchivedEstimatesForClient(@PathVariable int id) {
        List<Estimate> nonArchivedEstimates = contractService.getAllNonArchivedEstimatesForClient(id);
        return ResponseEntity.ok(nonArchivedEstimates);
    }


}



