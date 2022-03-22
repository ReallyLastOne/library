package reallylastone.librarymanagementsystem.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    @Setter
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiError(HttpStatus status) {
        this.status = status;
        timestamp = LocalDateTime.now();
    }
}
