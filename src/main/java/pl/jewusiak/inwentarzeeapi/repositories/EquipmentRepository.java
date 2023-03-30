package pl.jewusiak.inwentarzeeapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Equipment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    Optional<Equipment> findEquipmentByqrCodeUuid(UUID qrcode);
}
