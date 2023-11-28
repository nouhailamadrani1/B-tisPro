package com.rentalhive.rentalhive.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
<<<<<<< HEAD

=======
>>>>>>> 4c030c66fcfd3623caa867f45110feba278af233
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@EstimateId")


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
