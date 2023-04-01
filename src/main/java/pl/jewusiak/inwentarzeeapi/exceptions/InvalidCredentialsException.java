package pl.jewusiak.inwentarzeeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidCredentialsException extends HttpStatusCodeException {

    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
