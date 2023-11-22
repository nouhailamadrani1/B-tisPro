package com.rentalhive.rentalhive.model;
import javax.persistence.*;
import java.util.Date;

@Table(name = "reservations")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private Date end_date;
    private Date start_date;
    @ManyToOne
    @JoinColumn(name = "id_equipment")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Reservation{\n" +
                "\"id\"=" + id + "\n" +
                ", \"end_date\"=" + end_date + "\n" +
                ", \"start_date\"=" + start_date + "\n" +
                ", \"equipment\"=" + equipment + "\n" +
                ", \"client\"=" + client + "\n" +
                '}';
    }
}