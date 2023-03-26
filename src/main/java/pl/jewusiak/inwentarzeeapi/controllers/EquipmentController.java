package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.Equipment;
import pl.jewusiak.inwentarzeeapi.services.EquipmentService;

@RestController
@RequestMapping("equipment")
public class EquipmentController {


    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("")
    public @ResponseBody Iterable<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public @ResponseBody Equipment getEquipmentById(@PathVariable long id) {
        return equipmentService.getEquipmentById(id);
    }

}
