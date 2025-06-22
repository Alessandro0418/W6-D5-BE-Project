package it.epicode.W6_D5_BE_Project.exeption;

import it.epicode.W6_D5_BE_Project.model.ApiError;
import jakarta.validation.ValidationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice //è un controller che gestisce gli errori
public class CustomizedExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)//serve per mappare il metodo che gestisce questa eccezione
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandler(ChangeSetPersister.NotFoundException e){
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(ValidationException.class)//serve per mappare il metodo che gestisce questa eccezione
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationExceptionHandler(ValidationException e){
        ApiError error = new ApiError();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }
}