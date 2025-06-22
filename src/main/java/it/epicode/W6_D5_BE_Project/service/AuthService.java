package it.epicode.W6_D5_BE_Project.service;

import it.epicode.W6_D5_BE_Project.dto.LoginDto;
import it.epicode.W6_D5_BE_Project.exeption.NotFoundExeption;
import it.epicode.W6_D5_BE_Project.model.User;
import it.epicode.W6_D5_BE_Project.repository.UserRepository;
import it.epicode.W6_D5_BE_Project.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NotFoundExeption {
        User user = userRepository.findByEmail(loginDto.getUsername())
                .orElseThrow(() -> new NotFoundExeption("Utente con questo username/password non trovato"));

        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return jwtTool.createToken(user);
        } else {
            throw new NotFoundExeption("Utente con questo username/password non trovato");
        }
    }
}