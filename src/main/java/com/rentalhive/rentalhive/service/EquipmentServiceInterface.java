package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Equipment;

import java.util.List;
import java.util.Optional;
public interface EquipmentServiceInterface {

    List<Equipment> getAllEquipment();

    Optional<Equipment> getEquipmentById(int id);

    Equipment addEquipment(Equipment equipment);

    Equipment updateEquipment(int id, Equipment equipment);

    void deleteEquipment(int id);

    Equipment getEquipmentByName(String name);
}
