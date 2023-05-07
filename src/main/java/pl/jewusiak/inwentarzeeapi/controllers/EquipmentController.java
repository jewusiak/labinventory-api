package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipment) {
        return ResponseEntity.status(201).body(equipmentMapper.mapToDto(equipmentService.createEquipment(equipmentMapper.mapToEntity(equipment))));
    }

    @PutMapping("/{id}")
    public EquipmentDto updateEquipment(@RequestBody EquipmentDto equipmentUpdateDto, @PathVariable Long id) {
        return equipmentMapper.mapToDto(equipmentService.updateEquipment(equipmentUpdateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/search")
    public Collection<EquipmentDto> doSearch(@RequestParam(required = false) String id, @RequestParam(required = false) String location, @RequestParam(required = false) String name, @RequestParam(required = false) String searchInDescription) {
        boolean _searchInDescription = "true".equals(searchInDescription) || "1".equals(searchInDescription);
        Long _id = null;
        if (id != null)
            try {
                _id = Long.valueOf(id);
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID has to be a number.");
            }
        return equipmentMapper.mapAllToDto(equipmentService.doSearch(_id, location, name, _searchInDescription));
    }
}
