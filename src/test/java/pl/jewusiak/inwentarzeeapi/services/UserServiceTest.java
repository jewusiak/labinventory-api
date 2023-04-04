package pl.jewusiak.inwentarzeeapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.jewusiak.inwentarzeeapi.exceptions.DuplicateEmailExistsInDatabase;
import pl.jewusiak.inwentarzeeapi.exceptions.InvalidCredentialsException;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.repositories.UserRepository;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static User exampleUser = User.builder().email("john.doe@example.com").displayName("John Doe").password("pass").id(1).build();
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldFindOptionalUserByEmail() {
        when(userRepository.findUserByEmail(exampleUser.getEmail())).thenReturn(Optional.of(exampleUser));

        assertThat(exampleUser).isEqualTo(userService.findUserByEmail(exampleUser.getEmail()).orElse(null));
    }

    @Test
    void shouldGetUserByEmail() {
        when(userRepository.findUserByEmail(exampleUser.getEmail())).thenReturn(Optional.of(exampleUser));

        assertThat(userService.getUserByEmail(exampleUser.getEmail())).isEqualTo(exampleUser);
    }

    @Test
    void shouldThrowWhenNotFound_getUserByEmail() {
        when(userRepository.findUserByEmail(exampleUser.getEmail())).thenReturn(empty());

        assertThatThrownBy(() -> userService.getUserByEmail(exampleUser.getEmail())).isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void shouldReturnEmptyOptional_findUserByEmail() {
        when(userRepository.findUserByEmail(exampleUser.getEmail())).thenReturn(empty());

        assertThat(userService.findUserByEmail(exampleUser.getEmail())).isEqualTo(empty());
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail(exampleUser.getEmail())).thenReturn(false);
        when(userRepository.save(exampleUser)).thenReturn(exampleUser);

        assertThat(userService.createUser(exampleUser)).isEqualTo(exampleUser);
    }

    @Test
    void createUser_onDuplicateEmail() {
        when(userRepository.existsByEmail(exampleUser.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(exampleUser)).isInstanceOf(DuplicateEmailExistsInDatabase.class);
    }

    @Test
    void changeUserActivationStatus_onSuccess() {
        exampleUser.setAccountEnabled(false);
        when(userRepository.findById(exampleUser.getId())).thenReturn(Optional.ofNullable(exampleUser));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        userService.changeUserActivationStatus(1, true);
        verify(userRepository).save(captor.capture());

        assertThat(captor.getValue().isEnabled()).isEqualTo(true);
    }

    @Test
    void changeUserActivationStatus_onNonExistingUser() {
        when(userRepository.findById(exampleUser.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.changeUserActivationStatus(exampleUser.getId(), true)).isInstanceOf(NotFoundException.class);
    }

}