package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Files,Integer> {

    public List<Files> findByRentalRequestId(int rentalRequestId);
}
