package pl.jewusiak.inwentarzeeapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.InvalidCredentialsException;
import pl.jewusiak.inwentarzeeapi.exceptions.JWTExpiryTimeNotInRangeException;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.models.auth.AuthenticationRequest;
import pl.jewusiak.inwentarzeeapi.models.auth.AuthenticationResponse;
import pl.jewusiak.inwentarzeeapi.models.auth.RegisterRequest;
import pl.jewusiak.inwentarzeeapi.models.mappers.UserMapper;
import pl.jewusiak.inwentarzeeapi.security.JwtService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getJwtexpirytime() == null) request.setJwtexpirytime(1);
        if (request.getJwtexpirytime() < 1 || request.getJwtexpirytime() > 30)
            throw new JWTExpiryTimeNotInRangeException();

        var user = User.builder().displayName(request.getDisplayName()).email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).isAccountEnabled(false)
                .role(User.UserRole.USER).build();
        User createdUser = userService.createUser(user);
        var jwtToken = jwtService.generateToken(user, Duration.ofDays(request.getJwtexpirytime()));
        return AuthenticationResponse.builder().token(jwtToken).user(userMapper.mapToDto(createdUser)).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (request.getJwtexpirytime() == null) request.setJwtexpirytime(1);
        if (request.getJwtexpirytime() < 1 || request.getJwtexpirytime() > 30)
            throw new JWTExpiryTimeNotInRangeException();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
        var user = userService.findUserByEmail(request.getEmail()).orElseThrow(InvalidCredentialsException::new);

        var jwtToken = jwtService.generateToken(user, Duration.ofDays(request.getJwtexpirytime()));
        return AuthenticationResponse.builder().token(jwtToken).user(userMapper.mapToDto(user)).build();
    }
}
