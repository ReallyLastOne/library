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
        return createResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    public ResponseEntity<ApiError> handleEntityAlreadyExistException(EntityAlreadyExist ex) {
        return createResponseEntity(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleWrongDataException(InvalidRequestException ex) {
        return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, ex);
    }

    private ResponseEntity<ApiError> createResponseEntity(HttpStatus status, Exception ex) {
        ApiError error = new ApiError(status);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }
}