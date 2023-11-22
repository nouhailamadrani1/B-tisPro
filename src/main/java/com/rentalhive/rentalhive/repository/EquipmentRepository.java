package com.rentalhive.rentalhive.repository;

import com.rentalhive.rentalhive.model.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EquipmentRepository extends CrudRepository<Equipment,Integer> {

    public Equipment findByName(String name);


}