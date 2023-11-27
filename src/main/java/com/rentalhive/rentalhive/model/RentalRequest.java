package com.rentalhive.rentalhive.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@RentalRequestId")

public class RentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date start_date;
    private Date end_date;
    @Enumerated(EnumType.STRING)
    private RentalRequestStatus rentalRequestStatus;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @OneToMany(mappedBy = "rentalRequest")
    private List<Estimate> estimates;


}
