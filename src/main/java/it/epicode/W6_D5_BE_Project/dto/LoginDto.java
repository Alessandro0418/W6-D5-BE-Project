package it.epicode.W6_D5_BE_Project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;

    @NotEmpty(message = "la password non può essere vuoto")
    private String password;
}
