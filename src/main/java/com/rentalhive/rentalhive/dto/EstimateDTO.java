package com.rentalhive.rentalhive.dto;


import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class EstimateDTO {

    private int id;
    private Double estimatedCost;
    private EstimateStatus estimateStatus;

    private RentalRequest rentalRequest;

    private User admin;

    private boolean archived;


}
