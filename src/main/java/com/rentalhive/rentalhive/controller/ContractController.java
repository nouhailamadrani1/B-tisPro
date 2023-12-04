package com.rentalhive.rentalhive.controller;

import com.rentalhive.rentalhive.dto.ContractDTO;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.service.ContractServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/estimates")
public class ContractController {

    @Autowired
    private ContractServiceInterface contractService;

    @GetMapping("/getContractPDF/{id}")
    public ResponseEntity<byte[]> getContractPDF(@PathVariable int id) {
        try {
            byte[] pdfContent = contractService.generateContractPDF(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=contract.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating contract PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/getAllApprovedEstimatesForClient/{id}")
    public ResponseEntity<List<ContractDTO>> getAllApprovedEstimatesForClient(@PathVariable int id) {
        List<ContractDTO> approvedEstimates = contractService.getAllApprovedEstimatesForClient(id);
        return ResponseEntity.ok(approvedEstimates);
    }

    @PutMapping("/archiveContract/{id}")
    public ResponseEntity<String> archiveContract(@PathVariable int id) {
        return ResponseEntity.ok(contractService.archiveContract(id));
    }

    @GetMapping("/getAllNonArchivedEstimatesForClient/{id}")
    public ResponseEntity<List<ContractDTO>> getAllNonArchivedEstimatesForClient(@PathVariable int id) {
        List<ContractDTO> nonArchivedEstimates = contractService.getAllNonArchivedEstimatesForClient(id);
        return ResponseEntity.ok(nonArchivedEstimates);
    }
}
