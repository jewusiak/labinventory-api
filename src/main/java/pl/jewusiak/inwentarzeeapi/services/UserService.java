package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.inwentarzeeapi.exceptions.DuplicateEmailExistsInDatabase;
import pl.jewusiak.inwentarzeeapi.exceptions.InvalidCredentialsException;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.jwt.JwtService;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(InvalidCredentialsException::new);
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) throw new DuplicateEmailExistsInDatabase();
        return userRepository.save(user);
    }

    public User changeUserActivationStatus(long id, boolean setEnabled) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user by id"));
        user.setIsAccountEnabled(setEnabled);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NotFoundException("user by id")));
    }

    public User getUserByToken(String token) {
        if (token == null || !token.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        token = token.substring(7);
        if (jwtService.isTokenExpired(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return getUserByEmail(jwtService.extractEmail(token));
    }
}
