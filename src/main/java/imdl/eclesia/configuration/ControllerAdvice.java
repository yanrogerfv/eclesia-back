package imdl.eclesia.configuration;

import imdl.eclesia.domain.exception.*;
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
    public ApiError handleEntityNotFoundException(EntityNotFoundException exception){
        String message = exception.getMessage();
        return new ApiError(message);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleApiError(UnauthorizedException exception){
        String message = exception.getMessage();
        return new ApiError(message);
    }

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleApiError(PermissionException exception) {
        String message = exception.getMessage();
        return new ApiError(message);
    }
}
