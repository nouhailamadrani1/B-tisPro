package com.rentalhive.rentalhive.model;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int quantity;
    private Double price;
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

}
