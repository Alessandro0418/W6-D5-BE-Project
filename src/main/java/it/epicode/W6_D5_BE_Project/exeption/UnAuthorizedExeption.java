package it.epicode.W6_D5_BE_Project.exeption;

public class UnAuthorizedExeption extends RuntimeException {
    public UnAuthorizedExeption(String message) {
        super(message);
    }
}
