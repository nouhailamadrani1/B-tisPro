package com.rentalhive.rentalhive.controller;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/getContract/{id}")
    public ResponseEntity<String> getContract(@PathVariable int id) {
        String contractDetails = contractService.checkEstimateStatusUpdate(id);
        return ResponseEntity.ok(contractDetails);
    }
}



