package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface RentalRequestRepository extends JpaRepository<RentalRequest, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 'FALSE' ELSE 'TRUE' END FROM rental_request WHERE equipment_id = :equipment_id AND (:start_date BETWEEN start_date AND end_date OR :end_date BETWEEN start_date AND end_date OR start_date BETWEEN :start_date AND :end_date)", nativeQuery = true)
    boolean isAvailable(
            @Param("equipment_id") int equipment_id,
            @Param("start_date") Date startDate,
            @Param("end_date") Date endDate
    );
}
