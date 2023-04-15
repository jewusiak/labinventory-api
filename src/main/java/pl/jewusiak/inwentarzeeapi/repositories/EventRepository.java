package pl.jewusiak.inwentarzeeapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Event;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Override
    List<Event> findAll();
}
