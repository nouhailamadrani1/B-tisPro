package com.rentalhive.rentalhive.model;



import javax.persistence.*;
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public User(int id, String name, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public User(Integer o, String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }



    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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

    @Override
    public String toString() {
        return "User{" +
                "\"id\"=" + id +  ",\n" +
                "\"name\"=" + name +  ",\n" +
                "\"isAdmin=\"" + isAdmin + "\n" +
                '}';
    }
}