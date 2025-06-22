package it.epicode.W6_D5_BE_Project.exeption;

public class ValidationExeption extends RuntimeException {
    public ValidationExeption(String message) {
        super(message);
    }
}
