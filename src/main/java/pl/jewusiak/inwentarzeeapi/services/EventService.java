package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Event;
import pl.jewusiak.inwentarzeeapi.repositories.EventRepository;

@Service
public class EventService {


    private final EventRepository eventRepository;
    private final EquipmentService equipmentService;

    public EventService(EventRepository eventRepository, EquipmentService equipmentService) {
        this.eventRepository = eventRepository;
        this.equipmentService = equipmentService;
    }

    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("event"));
    }

    public void deleteEventById(long id) {
        eventRepository.delete(getEventById(id));
    }

    public Event createEvent(Event event) {
        event.setId(-1);
        return eventRepository.save(event);
    }

    public Event assignEquipment(long eventId, long equipmentId) {
        Event event = getEventById(eventId);
        event.setEquipment(equipmentService.getEquipmentById(equipmentId));
        return eventRepository.save(event);
    }

}
