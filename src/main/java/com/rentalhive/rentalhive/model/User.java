package com.rentalhive.rentalhive.model;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Role role;
    private String email;
    private String numberPhone;

}