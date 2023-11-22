package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.Reservation;
import com.rentalhive.rentalhive.model.User;
import com.rentalhive.rentalhive.repository.EquipmentRepository;
import com.rentalhive.rentalhive.repository.ReservationRepository;
import com.rentalhive.rentalhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }



    public Reservation updateResevration(int id, Reservation reservation)    {
        reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + id));

        equipmentRepository.findById(reservation.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with ID: " + reservation.getEquipment().getId()));

        userRepository.findById(reservation.getClient().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reservation.getClient().getId()));


        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    public Reservation addResevration(Reservation reservation) {

        equipmentRepository.findById(reservation.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with ID: " + reservation.getEquipment().getId()));

        userRepository.findById(reservation.getClient().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reservation.getClient().getId()));

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByEquipmentId(int id) {
        System.out.println("id: "+id);
        equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with ID: " + id));
        return reservationRepository.findByEquipment_Id(id);
    }


}
