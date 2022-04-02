package reallylastone.librarymanagementsystem.exceptions;

public class RequestNotAllowedException extends RuntimeException {
    public RequestNotAllowedException(String msg) {
        super(msg);
    }
}
