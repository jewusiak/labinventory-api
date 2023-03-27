package pl.jewusiak.inwentarzeeapi.exceptions;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException() {
        super(NOT_FOUND, "Entity not found in the database.");
    }
}
