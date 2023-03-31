package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.Equipment;
import pl.jewusiak.inwentarzeeapi.services.EquipmentService;

import java.util.UUID;

@RestController
@RequestMapping(value = "equipment")
public class EquipmentController {


    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/getall")
    public Iterable<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/getbyid/{id}")
    public Equipment getEquipmentById(@PathVariable long id) {
        return equipmentService.getEquipmentById(id);
    }

    @PostMapping(value = "")
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        return equipmentService.createEquipment(equipment);
    }

    @PutMapping("/{id}")
    public Equipment updateEquipment(@RequestBody String json, @PathVariable long id) {
        return equipmentService.updateEquipment(json, id);
    }

    @DeleteMapping("/{id}")
    public void deleteEquipment(@PathVariable long id) {
        equipmentService.deleteEquipmentById(id);
    }

    @GetMapping("/genqrcode/{id}")
    public ResponseEntity<Resource> getQrCode(@PathVariable long id) throws Exception {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qr.png\"").header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(equipmentService.generateQrCode(getEquipmentById(id)));
    }

    @GetMapping("/getbyqrcode/{uuid}")
    public Equipment getByQrCode(@PathVariable UUID uuid) {
        return equipmentService.getEquipmentByQrCodeUUID(uuid);
    }

}
