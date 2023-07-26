package pl.jewusiak.inwentarzeeapi.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;
import pl.jewusiak.inwentarzeeapi.models.mappers.UserMapper;
import pl.jewusiak.inwentarzeeapi.services.UserService;

@Tag(name = "User management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/myprofile")
    public UserDto getOwnProfile(@RequestHeader("Authorization") String bearerToken) {
        return userMapper.mapToDto(userService.getUserByToken(bearerToken));
    }

    @PutMapping("/admin/{id}/{setEnabled}")
    public UserDto changeUserActivationStatus(@PathVariable Long id, @PathVariable int setEnabled) {
        if (setEnabled != 0 && setEnabled != 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Endpoint use: /users/{user id}/{0 or 1}. 0=false, 1=true.");
        return userMapper.mapToDto(userService.changeUserActivationStatus(id, setEnabled == 1));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
