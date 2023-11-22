package com.rentalhive.rentalhive.model;

import javax.persistence.*;


@Table(name = "equipments")
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int quantity;
    private EquipmentStatus status;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "\"id\"=" + id +  ",\n" +
                "\"name\"=" + name +  ",\n" +
                "\"quantity\"=" + quantity +  ",\n" +
                "\"status\"=" + status + "\n" +
                '}';
    }
}
