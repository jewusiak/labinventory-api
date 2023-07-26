package pl.jewusiak.inwentarzeeapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Event;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EquipmentService equipmentService;


    public Collection<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("event by id"));
    }

    public void deleteEventById(long id) {
        eventRepository.delete(getEventById(id));
    }

    public Event createEvent(Event event, User createdBy) {
        event.setId(null);
        event.setDateCreated(LocalDateTime.now(ZoneId.of("Europe/Warsaw")));
        event.setCreatedBy(createdBy);
        return eventRepository.save(event);
    }

    public Event assignEquipment(long eventId, Long equipmentId) {
        Event event = getEventById(eventId);
        event.setEquipment(equipmentService.getEquipmentById(equipmentId));
        return eventRepository.save(event);
    }

}
