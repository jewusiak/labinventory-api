package pl.jewusiak.inwentarzeeapi.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.inwentarzeeapi.models.dtos.EventDto;
import pl.jewusiak.inwentarzeeapi.models.mappers.EventMapper;
import pl.jewusiak.inwentarzeeapi.services.EventService;
import pl.jewusiak.inwentarzeeapi.services.UserService;

import java.util.Collection;

@Tag(name = "Events", description = "Manage events regarding lab equipment")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserService userService;


    @GetMapping("")
    public Collection<EventDto> getAllEvents() {
        return eventMapper.mapAllToDto(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Long id) {
        return eventMapper.mapToDto(eventService.getEventById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto event, @RequestHeader("Authorization") String bearerToken) {
        return ResponseEntity.status(201).body(eventMapper.mapToDto(eventService.createEvent(eventMapper.mapToEntity(event), userService.getUserByToken(bearerToken))));
    }

    @PostMapping("{event_id}/assign_equipment/{equipment_id}")
    public EventDto assignEquipment(@PathVariable Long event_id, @PathVariable Long equipment_id) {
        return eventMapper.mapToDto(eventService.assignEquipment(event_id, equipment_id));
    }

}
