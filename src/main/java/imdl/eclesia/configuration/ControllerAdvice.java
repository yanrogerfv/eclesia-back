package imdl.eclesia.configuration;

import imdl.eclesia.domain.exception.ApiError;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RogueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleApiError(RogueException exception){
        String message = exception.getMessage();
        return new ApiError(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleApiError(EntityNotFoundException exception){
        String message = exception.getMessage();
        return new ApiError(message);
    }
}
