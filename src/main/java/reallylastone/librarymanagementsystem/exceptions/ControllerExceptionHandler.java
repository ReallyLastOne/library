package reallylastone.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(EntityNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    public ResponseEntity<ApiError> handleEntityAlreadyExistException(EntityAlreadyExist ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleWrongDataException(InvalidRequestException ex) {
        ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }
}