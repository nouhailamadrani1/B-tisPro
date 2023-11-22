package com.rentalhive.rentalhive.controller;


import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getEquipmentById(@PathVariable int id) {
        return equipmentService.getEquipmentById(id);
    }

    @PostMapping
    public Equipment addEquipment(@RequestBody Equipment equipment) {
        return equipmentService.addEquipment(equipment);
    }

    @PutMapping("/{id}")
    public Equipment updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        return equipmentService.updateEquipment(id, equipment);
    }

    @DeleteMapping("/{id}")
    public void deleteEquipment(@PathVariable int id) {
        equipmentService.deleteEquipment(id);
    }

    @GetMapping("/E-name/{name}")
    public Equipment getEquipmentByName(@PathVariable String name) {
        return equipmentService.getEquipmentByName(name);
    }
}
