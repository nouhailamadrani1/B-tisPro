package com.rentalhive.rentalhive.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String path;
    private String entity;
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "rental_request_id")
    private RentalRequest rentalRequest;
    @ManyToOne
    @JoinColumn(name = "estimate_id")
    private Estimate estimate;
}
