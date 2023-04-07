package pl.jewusiak.inwentarzeeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JWTExpiryTimeNotInRangeException extends ResponseStatusException {
    public JWTExpiryTimeNotInRangeException() {
        super(HttpStatus.BAD_REQUEST, "JWT time has to be between: 1 and 30 (days)");
    }
}
