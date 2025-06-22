package it.epicode.W6_D5_BE_Project.repository;

import it.epicode.W6_D5_BE_Project.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}