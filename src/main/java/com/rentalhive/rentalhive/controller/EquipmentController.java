package com.rentalhive.rentalhive.controller;


import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.service.impl.EquipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentServiceImpl equipmentServiceImpl;

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentServiceImpl.getAllEquipment();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getEquipmentById(@PathVariable int id) {
        return equipmentServiceImpl.getEquipmentById(id);
    }

    @PostMapping
    public Equipment addEquipment(@RequestBody Equipment equipment) {
        return equipmentServiceImpl.addEquipment(equipment);
    }

    @PutMapping("/{id}")
    public Equipment updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        return equipmentServiceImpl.updateEquipment(id, equipment);
    }

    @DeleteMapping("/{id}")
    public void deleteEquipment(@PathVariable int id) {
        equipmentServiceImpl.deleteEquipment(id);
    }

    @GetMapping("/E-name/{name}")
    public Equipment getEquipmentByName(@PathVariable String name) {
        return equipmentServiceImpl.getEquipmentByName(name);
    }
}
