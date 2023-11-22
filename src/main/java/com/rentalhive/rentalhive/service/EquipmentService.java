package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.exceptions.*;
import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.EquipmentStatus;
import com.rentalhive.rentalhive.model.Reservation;
import com.rentalhive.rentalhive.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {
        return (List<Equipment>) equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(int id) {
        return equipmentRepository.findById(id);
    }

    public Equipment addEquipment(Equipment equipment) {
        // Validation
        validateEquipmentName(equipment.getName());
        validateEquipmentQuantity(equipment.getQuantity());
        validateEquipmentPrice(equipment.getPrice());
        validateEquipmentStatus(equipment.getStatus());

        // Save the equipment to the database
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(int id, Equipment equipment) {
        // Check if equipment with the given ID exists
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment with ID " + id + " not found."));

        // Validate the name, quantity, price, and status before updating
        validateEquipmentName(equipment.getName());
        validateEquipmentQuantity(equipment.getQuantity());
        validateEquipmentPrice(equipment.getPrice());
        validateEquipmentStatus(equipment.getStatus());

        // Update the existing equipment
        existingEquipment.setName(equipment.getName());
        existingEquipment.setQuantity(equipment.getQuantity());
        existingEquipment.setPrice(equipment.getPrice());
        existingEquipment.setStatus(equipment.getStatus());

        // Save the updated equipment to the database
        return equipmentRepository.save(existingEquipment);
    }


    public void deleteEquipment(int id) {
        equipmentRepository.deleteById(id);
    }

    public Equipment getEquipmentByName(String name) {
        return equipmentRepository.findByName(name);
    }

    private void validateEquipmentName(String name) {
        if (!name.matches("^[a-zA-Z ]+$")) {
            throw new InvalidEquipmentNameException("Equipment name must contain only letters (upper or lowercase).");
        }
    }

    private void validateEquipmentQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidEquipmentQuantityException("Equipment quantity must be a non-negative number.");
        }
    }

    private void validateEquipmentPrice(Double price) {
        if (price == null || price < 0) {
            throw new InvalidEquipmentPriceException("Equipment price must be a non-negative double.");
        }
    }

    private void validateEquipmentStatus(EquipmentStatus status) {
        if (status == null) {
            throw new InvalidEquipmentStatusException("Equipment status must not be null.");
        }
    }
}
