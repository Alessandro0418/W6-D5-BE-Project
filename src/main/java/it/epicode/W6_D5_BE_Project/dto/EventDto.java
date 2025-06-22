package it.epicode.W6_D5_BE_Project.dto;

import lombok.Data;

@Data
public class EventDto {
    private String title;
    private String description;
    private String date;
    private String location;
    private int seatsAvailable;
}
