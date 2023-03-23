package pl.jewusiak.inwentarzeeapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Attachment;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
