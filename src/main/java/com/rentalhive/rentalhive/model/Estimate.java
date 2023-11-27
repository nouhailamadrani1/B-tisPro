package com.rentalhive.rentalhive.model;

import com.rentalhive.rentalhive.service.ContractService;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity


public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double estimatedCost;
    @Enumerated(EnumType.STRING)
    private EstimateStatus estimateStatus;
    @ManyToOne
    @JoinColumn(name = "rental_request_id")
    private RentalRequest rentalRequest;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;
    private boolean archived;
}
