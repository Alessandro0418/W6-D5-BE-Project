package it.epicode.W6_D5_BE_Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private LocalDate date;

    private String location;

    private int seatsAvailable;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "event")
    private List<Booking> bookings;

}
