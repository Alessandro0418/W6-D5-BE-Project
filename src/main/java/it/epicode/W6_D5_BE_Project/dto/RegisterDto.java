package it.epicode.W6_D5_BE_Project.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
