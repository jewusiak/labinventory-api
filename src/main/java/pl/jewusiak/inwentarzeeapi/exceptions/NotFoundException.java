package pl.jewusiak.inwentarzeeapi.exceptions;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String entity) {
        super(NOT_FOUND, "Entity of class " + entity + " wasn't found in the database.");

    }
}
