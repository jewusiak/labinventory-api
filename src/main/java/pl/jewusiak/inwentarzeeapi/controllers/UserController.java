package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;
import pl.jewusiak.inwentarzeeapi.services.UserService;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/{setEnabled}")
    public UserDto changeUserActivationStatus(@PathVariable long id, @PathVariable int setEnabled) {
        if (setEnabled != 0 && setEnabled != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endpoint use: /users/{user id}/{0 or 1}. 0=false, 1=true.");
        return userService.changeUserActivationStatus(id, setEnabled == 1).toDto();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
