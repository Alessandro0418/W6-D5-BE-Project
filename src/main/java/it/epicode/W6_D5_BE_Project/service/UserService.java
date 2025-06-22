package it.epicode.W6_D5_BE_Project.service;

import it.epicode.W6_D5_BE_Project.dto.EventDto;
import it.epicode.W6_D5_BE_Project.dto.RegisterDto;
import it.epicode.W6_D5_BE_Project.dto.UserDto;
import it.epicode.W6_D5_BE_Project.enumerating.Role;
import it.epicode.W6_D5_BE_Project.model.Booking;
import it.epicode.W6_D5_BE_Project.model.Event;
import it.epicode.W6_D5_BE_Project.repository.BookingRepository;
import it.epicode.W6_D5_BE_Project.repository.EventRepository;
import it.epicode.W6_D5_BE_Project.repository.UserRepository;
import it.epicode.W6_D5_BE_Project.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.epicode.W6_D5_BE_Project.dto.EventDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final BookingRepository bookingRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository ur, EventRepository er, BookingRepository br, PasswordEncoder encoder) {
        this.userRepo = ur;
        this.eventRepo = er;
        this.bookingRepo = br;
        this.encoder = encoder;
    }

    public void register(RegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.UTENTE);
        userRepo.save(user);
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepo.save(user);
    }


    public void createEvent(EventDto dto, String email) {
        User creator = userRepo.findByEmail(email).orElseThrow();
        if (creator.getRole() != Role.ORGANIZZATORE)
            throw new RuntimeException("Non autorizzato");

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setSeatsAvailable(dto.getSeatsAvailable());
        event.setDate(LocalDate.parse(dto.getDate()));
        event.setCreator(creator);
        eventRepo.save(event);
    }

    public void bookEvent(Long eventId, String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        Event event = eventRepo.findById(eventId).orElseThrow();

        if (bookingRepo.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("Hai già prenotato");
        }

        long booked = bookingRepo.countByEvent(event);
        if (booked >= event.getSeatsAvailable()) {
            throw new RuntimeException("Posti terminati");
        }

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        bookingRepo.save(booking);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}