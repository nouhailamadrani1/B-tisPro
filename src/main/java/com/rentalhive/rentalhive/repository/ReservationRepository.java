package com.rentalhive.rentalhive.repository;


import com.rentalhive.rentalhive.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation,Integer> {

    public List<Reservation> findByEquipment_Id(int equipmentId);
}
