package pl.jewusiak.inwentarzeeapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Equipment;
import pl.jewusiak.inwentarzeeapi.repositories.EquipmentRepository;

@Service
public class EquipmentService {


    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Iterable<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(long id) {
        return equipmentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteEquipmentById(long id) {
        equipmentRepository.delete(getEquipmentById(id));
    }

    public Equipment createEquipment(Equipment equipment) {
        equipment.setId(-1);
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(String json, long id) {
        Equipment oldEquipment = getEquipmentById(id);
        Equipment finalEquipment = null;
        try {
            finalEquipment = new ObjectMapper().readerForUpdating(oldEquipment).readValue(json);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed json.");
        }
        finalEquipment.setId(id);
        return equipmentRepository.save(finalEquipment);
    }
}
