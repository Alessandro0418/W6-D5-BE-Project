package it.epicode.W6_D5_BE_Project.repository;

import it.epicode.W6_D5_BE_Project.model.Booking;
import it.epicode.W6_D5_BE_Project.model.Event;
import it.epicode.W6_D5_BE_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    long countByEvent(Event event);
}