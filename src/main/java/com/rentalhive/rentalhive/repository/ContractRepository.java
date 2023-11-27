package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.RentalRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends CrudRepository<Estimate,Integer> {
    Estimate findById(int id);

}
