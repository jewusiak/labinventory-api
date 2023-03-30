package pl.jewusiak.inwentarzeeapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Equipment;
import pl.jewusiak.inwentarzeeapi.repositories.EquipmentRepository;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

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
        return equipmentRepository.findById(id).orElseThrow(() -> new NotFoundException("equipment"));
    }

    public void deleteEquipmentById(long id) {
        equipmentRepository.delete(getEquipmentById(id));
    }

    public Equipment createEquipment(Equipment equipment) {
        equipment.setId(-1);
        equipment.setQrCodeUuid(UUID.randomUUID());
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(String json, long id) {
        Equipment oldEquipment = getEquipmentById(id);
        Equipment finalEquipment;
        try {
            finalEquipment = new ObjectMapper().readerForUpdating(oldEquipment).readValue(json);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed json.");
        }
        finalEquipment.setId(id);
        return equipmentRepository.save(finalEquipment);
    }

    public Resource generateQrCode(long id) throws Exception {

        BitMatrix matrix = new QRCodeWriter().encode(getEquipmentById(id).getQrCodeUuid().toString(), BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", stream);
        return new ByteArrayResource(stream.toByteArray());
    }

    public Equipment getEquipmentByQrCodeUUID(UUID uuid) {
        return equipmentRepository.findEquipmentByqrCodeUuid(uuid).orElseThrow(() -> new NotFoundException("equipment (by qr code)"));
    }
}
