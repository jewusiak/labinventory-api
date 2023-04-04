package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.DuplicateEmailExistsInDatabase;
import pl.jewusiak.inwentarzeeapi.exceptions.InvalidCredentialsException;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(InvalidCredentialsException::new);
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) throw new DuplicateEmailExistsInDatabase();
        user.setRole(User.UserRole.USER);
        user.setAccountEnabled(true);
        return userRepository.save(user);
    }

    public User changeUserActivationStatus(long id, boolean setEnabled) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user by id"));
        user.setAccountEnabled(setEnabled);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NotFoundException("user by id")));
    }

}
