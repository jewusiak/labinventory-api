package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.dtos.EventDto;
import pl.jewusiak.inwentarzeeapi.models.mappers.EventMapper;
import pl.jewusiak.inwentarzeeapi.services.EventService;

import java.util.Collection;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("")
    public Collection<EventDto> getAllEvents() {
        return eventMapper.mapAllToDto(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Long id) {
        return eventMapper.mapToDto(eventService.getEventById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable Long id) {
        eventService.deleteEventById(id);
    }

    @PostMapping("")
    public EventDto createEvent(@RequestBody EventDto event) {
        return eventMapper.mapToDto(eventService.createEvent(eventMapper.mapToEntity(event)));
    }

    @PostMapping("{event_id}/assign_equipment/{equipment_id}")
    public EventDto assignEquipment(@PathVariable Long event_id, @PathVariable Long equipment_id) {
        return eventMapper.mapToDto(eventService.assignEquipment(event_id, equipment_id));
    }

}
