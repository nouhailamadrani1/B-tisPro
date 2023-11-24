package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.RentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRequestRepository extends JpaRepository<RentalRequest, Integer> {
}
