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
    private EquipmentStatus status;
    private EquipmentType equipmentType;

}
