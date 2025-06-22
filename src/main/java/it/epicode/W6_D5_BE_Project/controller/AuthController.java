package it.epicode.W6_D5_BE_Project.controller;

import it.epicode.W6_D5_BE_Project.dto.LoginDto;
import it.epicode.W6_D5_BE_Project.dto.UserDto;
import it.epicode.W6_D5_BE_Project.exeption.NotFoundExeption;
import it.epicode.W6_D5_BE_Project.exeption.ValidationExeption;
import it.epicode.W6_D5_BE_Project.model.User;
import it.epicode.W6_D5_BE_Project.service.AuthService;
import it.epicode.W6_D5_BE_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationExeption {
        if (bindingResult.hasErrors()) {
            throw new ValidationExeption(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e));
        }
        return userService.saveUser(userDto);
    }


    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationExeption, NotFoundExeption {

        if (bindingResult.hasErrors()) {
            throw new ValidationExeption(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e));

        }
        return authService.login(loginDto);

    }
}
