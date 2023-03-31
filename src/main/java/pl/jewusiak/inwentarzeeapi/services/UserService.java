package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.repositories.UserRepository;

import java.util.Arrays;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("user by email"));
    }

    public User createUser(User user) {
        user.setRole(User.UserRole.USER);
        return userRepository.save(user);
    }

    public User changeUserActivationStatus(long id, boolean isEnabled){
        var user = getUserById(id);
        user.setAccountEnabled(isEnabled);
        return userRepository.save(user);
    }

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("user by id"));
    }

}
