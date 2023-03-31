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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    public Resource generateQrCode(Equipment equipment) throws Exception {
        String name=equipment.getName()==null ? "BRAK NAZWY" : equipment.getName();
        String id=equipment.getId()+"";

        BitMatrix matrix = new QRCodeWriter().encode(equipment.getQrCodeUuid().toString(), BarcodeFormat.QR_CODE, 200, 200);
        var qrCode = MatrixToImageWriter.toBufferedImage(matrix);

        int qrCodeWidth = qrCode.getWidth();
        int qrCodeHeight = qrCode.getHeight();
        int totalWidth = qrCodeWidth + 200;
        int totalHeight = qrCodeHeight + 60;

        BufferedImage image = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, totalWidth, totalHeight);

        graphics.setFont(new Font("Arial", Font.BOLD, 15));
        graphics.setColor(Color.BLACK);

        graphics.drawString("Nazwa: " + name, 20, 30);
        graphics.drawString("ID: " + id, 20, 60);

        graphics.drawImage(qrCode, 100, 80, null);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        byte[] result = stream.toByteArray();
        stream.close();

        return new ByteArrayResource(result);
    }

    public Equipment getEquipmentByQrCodeUUID(UUID uuid) {
        return equipmentRepository.findEquipmentByqrCodeUuid(uuid).orElseThrow(() -> new NotFoundException("equipment (by qr code)"));
    }
}
