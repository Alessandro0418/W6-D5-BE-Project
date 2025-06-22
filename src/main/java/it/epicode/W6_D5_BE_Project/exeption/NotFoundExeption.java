package it.epicode.W6_D5_BE_Project.exeption;

public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption(String message) {
        super(message);
    }
}
