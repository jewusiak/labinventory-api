package pl.jewusiak.inwentarzeeapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Attachment;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, UUID> {
    @Override
    List<Attachment> findAll();
}
