package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.Event;
import pl.jewusiak.inwentarzeeapi.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public Iterable<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable long id) {
        return eventService.getEventById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable long id) {
        eventService.deleteEventById(id);
    }

    @PostMapping("")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PostMapping("{event_id}/assign_equipment/{equipment_id}")
    public Event assignEquipment(@PathVariable long event_id, @PathVariable long equipment_id) {
        return eventService.assignEquipment(event_id, equipment_id);
    }


}
