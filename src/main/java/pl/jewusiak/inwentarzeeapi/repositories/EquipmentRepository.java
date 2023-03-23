package pl.jewusiak.inwentarzeeapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Equipment;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
}
