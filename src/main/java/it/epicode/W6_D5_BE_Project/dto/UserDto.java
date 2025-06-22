package it.epicode.W6_D5_BE_Project.dto;

import it.epicode.W6_D5_BE_Project.enumerating.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;

    @NotEmpty(message = "la password non può essere vuoto")
    private String password;

    private String email;

    private Role role;
}

