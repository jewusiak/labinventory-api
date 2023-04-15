package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.dtos.EquipmentDto;
import pl.jewusiak.inwentarzeeapi.models.mappers.EquipmentMapper;
import pl.jewusiak.inwentarzeeapi.services.EquipmentService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentMapper equipmentMapper;

    public EquipmentController(EquipmentService equipmentService, EquipmentMapper equipmentMapper) {
        this.equipmentService = equipmentService;
        this.equipmentMapper = equipmentMapper;
    }

    @GetMapping("/getall")
    public Collection<EquipmentDto> getAllEquipment() {
        return equipmentMapper.mapAllToDto(equipmentService.getAllEquipment());
    }

    @GetMapping("/getbyid/{id}")
    public EquipmentDto getEquipmentById(@PathVariable Long id) {
        return equipmentMapper.mapToDto(equipmentService.getEquipmentById(id));
    }

    @PostMapping(value = "")
    public EquipmentDto createEquipment(@RequestBody EquipmentDto equipment) {
        return equipmentMapper.mapToDto(equipmentService.createEquipment(equipmentMapper.mapToEntity(equipment)));
    }

    @PutMapping("/{id}")
    public EquipmentDto updateEquipment(@RequestBody EquipmentDto equipmentUpdateDto, @PathVariable Long id) {
        return equipmentMapper.mapToDto(equipmentService.updateEquipment(equipmentUpdateDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipmentById(id);
    }

    @GetMapping("/genqrcode/{id}")
    public ResponseEntity<Resource> getQrCode(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qr.png\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(equipmentService.generateQrCode(equipmentService.getEquipmentById(id)));
    }

    @GetMapping("/getbyqrcode/{uuid}")
    public EquipmentDto getByQrCode(@PathVariable UUID uuid) {
        return equipmentMapper.mapToDto(equipmentService.getEquipmentByQrCodeUUID(uuid));
    }

}
