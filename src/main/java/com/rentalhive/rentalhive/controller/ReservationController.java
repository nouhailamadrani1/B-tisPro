package com.rentalhive.rentalhive.controller;

import com.rentalhive.rentalhive.model.Reservation;
import com.rentalhive.rentalhive.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addReservation(@RequestBody Reservation resevration) {
        try {
            Reservation addedReservation = reservationService.addResevration(resevration);
            return ResponseEntity.ok("Reservation added successfully \n"+addedReservation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        try {
            reservationService.updateResevration(id, reservation);
            return ResponseEntity.ok("Reservation updated successfully \n"+reservation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body("Reservation not found with ID: " + id);
        }
    }

    @GetMapping("/equipment/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByEquipmentId(@PathVariable int id){
        try {
            List<Reservation> reservations = reservationService.getReservationsByEquipmentId(id);
            return ResponseEntity.ok(reservations);
        } catch (EntityNotFoundException e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
