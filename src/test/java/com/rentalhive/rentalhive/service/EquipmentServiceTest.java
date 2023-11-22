package com.rentalhive.rentalhive.service;

import com.rentalhive.rentalhive.model.Equipment;
import com.rentalhive.rentalhive.model.EquipmentStatus;
import com.rentalhive.rentalhive.repository.EquipmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EquipmentServiceTest {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    public void testGetAllEquipment() {
        // Assuming you have some equipment already in the database
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        assertNotNull(equipmentList);
        assertFalse(equipmentList.isEmpty());
    }

    @Test
    public void testGetEquipmentById() {
        // Assuming there is an equipment with ID 1 in the database
        Optional<Equipment> equipmentOptional = equipmentService.getEquipmentById(1);
        assertTrue(equipmentOptional.isPresent());
    }

    @Test
    public void testAddEquipment() {
        Equipment newEquipment = new Equipment();
        newEquipment.setName("Equipment AddTest");
        newEquipment.setQuantity(10);
        newEquipment.setPrice(200.0);
        newEquipment.setStatus(EquipmentStatus.AVAILABLE);

        Equipment addedEquipment = equipmentService.addEquipment(newEquipment);

        assertNotNull(addedEquipment);
        assertNotNull(addedEquipment.getId());

        // Verify that the equipment is in the database
        Optional<Equipment> retrievedEquipment = equipmentRepository.findById(addedEquipment.getId());
        assertTrue(retrievedEquipment.isPresent());
        assertEquals("New Equipment Test", retrievedEquipment.get().getName());
    }

    @Test
    public void testUpdateEquipment() {
        // Insert a new equipment into the database
        Equipment newEquipment = new Equipment();
        newEquipment.setName("Equipment UpdateTest");
        newEquipment.setQuantity(10);
        newEquipment.setStatus(EquipmentStatus.AVAILABLE);
        newEquipment.setPrice(88.0);
        Equipment addedEquipment = equipmentService.addEquipment(newEquipment);

        // Search for the equipment by EquipmentName
        Equipment foundEquipment = equipmentService.getEquipmentByName("Equipment UpdateTest");

        // Update the quantity
        foundEquipment.setQuantity(20);
        Equipment updatedEquipment = equipmentService.updateEquipment(foundEquipment.getId(), foundEquipment);

        assertNotNull(updatedEquipment);
        assertEquals(20, updatedEquipment.getQuantity());
    }

    @Test //this test will try to delete an equipment with id 1
    public void testDeleteEquipment() {
        // Assuming there is an equipment with ID 1 in the database
        equipmentService.deleteEquipment(1);

        // Verify that the equipment is no longer in the database
        Optional<Equipment> deletedEquipment = equipmentRepository.findById(1);
        assertTrue(deletedEquipment.isEmpty());
    }

    @Test //this test will insert to db an equipment and search for it by name
    public void testGetEquipmentByName() {
        // Assuming there is an equipment with the name "Sample Equipment" in the database
        Equipment sampleEquipment = new Equipment();
        sampleEquipment.setName("Sample Equipment");
        sampleEquipment.setQuantity(5);
        sampleEquipment.setStatus(EquipmentStatus.AVAILABLE);
        sampleEquipment.setPrice(100.0);
        equipmentRepository.save(sampleEquipment);

        // Perform the test
        Equipment foundEquipment = equipmentService.getEquipmentByName("Sample Equipment");

        assertNotNull(foundEquipment);
        assertEquals("Sample Equipment", foundEquipment.getName());
    }
}