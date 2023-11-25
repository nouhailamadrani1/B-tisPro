package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.Estimate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends CrudRepository<Estimate,Integer> {
    Estimate findById(int id);
}
