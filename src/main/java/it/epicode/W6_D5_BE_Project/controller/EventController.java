package it.epicode.W6_D5_BE_Project.controller;

import it.epicode.W6_D5_BE_Project.dto.EventDto;
import it.epicode.W6_D5_BE_Project.model.Event;
import it.epicode.W6_D5_BE_Project.model.User;
import it.epicode.W6_D5_BE_Project.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final UserService service;

    public EventController(UserService s) {
        this.service = s;
    }

    @GetMapping
    public List<Event> getAll() {
        return service.getAllEvents();
    }

    @PostMapping
    public void create(@RequestBody EventDto dto, Principal principal) {
        service.createEvent(dto, principal.getName());
    }
}