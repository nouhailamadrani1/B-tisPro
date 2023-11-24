package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.Estimate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepository extends CrudRepository<Estimate,Integer> {

}
