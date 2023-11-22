package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.Reservation;
import com.rentalhive.rentalhive.model.User;
import com.rentalhive.rentalhive.repository.EquipmentRepository;
import com.rentalhive.rentalhive.repository.ReservationRepository;
import com.rentalhive.rentalhive.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void getAllReservations() {
        Equipment equipment = new Equipment();
        equipment.setId(10);

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setEquipment(equipment);

        Reservation reservation1 = new Reservation();
        reservation.setId(2);
        reservation.setEquipment(equipment);

        List<Reservation> reservationList = List.of(reservation, reservation1);

        when(reservationRepository.findAll()).thenReturn(reservationList);

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getEquipment().getId());
        assertEquals(reservation, result.get(0));
        assertEquals(reservation1, result.get(1));

    }

    @Test
    void getReservationById() {
        Reservation reservation = new Reservation();
        reservation.setId(1);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1);

        assertEquals(1, result.get().getId());
    }

    @Test
    void updateResevration() {

        int id = 23;

        Equipment equipment = new Equipment();
        equipment.setId(10);

        User user = new User();
        user.setId(1);

        Reservation existingReservation = new Reservation();
        existingReservation.setId(id);
        existingReservation.setStart_date(java.sql.Date.valueOf("2021-01-01"));
        existingReservation.setEquipment(equipment);
        existingReservation.setClient(user);

        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(id);
        updatedReservation.setStart_date(java.sql.Date.valueOf("2021-01-02"));
        updatedReservation.setEquipment(equipment);
        updatedReservation.setClient(user);


        when(reservationRepository.findById(id)).thenReturn(Optional.of(existingReservation));
        when(equipmentRepository.findById(updatedReservation.getEquipment().getId())).thenReturn(Optional.of(equipment));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(reservationRepository.save(updatedReservation)).thenReturn(updatedReservation);

        Reservation result = reservationService.updateResevration(id, updatedReservation);

        assertEquals(java.sql.Date.valueOf("2021-01-02"), result.getStart_date());
        verify(reservationRepository, times(1)).findById(id);
        verify(reservationRepository, times(1)).save(updatedReservation);
    }
}