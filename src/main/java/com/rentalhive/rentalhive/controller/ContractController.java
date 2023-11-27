package com.rentalhive.rentalhive.controller;
import com.rentalhive.rentalhive.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping ("/getContract/{id}")
    public ResponseEntity<String> getContract(@PathVariable int id) {
        return ResponseEntity.ok(contractService.checkEstimateStatusUpdate(id));
    }
}



