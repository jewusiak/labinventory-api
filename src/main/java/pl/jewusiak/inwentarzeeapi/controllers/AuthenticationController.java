package pl.jewusiak.inwentarzeeapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jewusiak.inwentarzeeapi.models.auth.AuthenticationRequest;
import pl.jewusiak.inwentarzeeapi.models.auth.AuthenticationResponse;
import pl.jewusiak.inwentarzeeapi.models.auth.RegisterRequest;
import pl.jewusiak.inwentarzeeapi.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse registerNewUser(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticateUser(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
}
