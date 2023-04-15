package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Event;
import pl.jewusiak.inwentarzeeapi.repositories.EventRepository;

import java.util.Collection;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EquipmentService equipmentService;

    public EventService(EventRepository eventRepository, EquipmentService equipmentService) {
        this.eventRepository = eventRepository;
        this.equipmentService = equipmentService;
    }

    public Collection<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("event by id"));
    }

    public void deleteEventById(long id) {
        eventRepository.deleteById(id);
    }

    public Event createEvent(Event event) {
        event.setId(null);
        return eventRepository.save(event);
    }

    public Event assignEquipment(long eventId, Long equipmentId) {
        Event event = getEventById(eventId);
        event.setEquipment(equipmentService.getEquipmentById(equipmentId));
        return eventRepository.save(event);
    }

}
