package it.epicode.W6_D5_BE_Project.controller;

import it.epicode.W6_D5_BE_Project.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.W6_D5_BE_Project.dto.BookingDto;

import java.awt.print.Book;
import java.security.Principal;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final UserService service;

    public BookingController(UserService s) {
        this.service = s;
    }

    @PostMapping
    public void book(@RequestBody BookingDto dto, Principal principal) {
        service.bookEvent(dto.getEventId(), principal.getName());
    }

}