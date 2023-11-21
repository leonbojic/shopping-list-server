package leonbojic.shoppinglistserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leonbojic.shoppinglistserver.exception.InvalidRequestException;
import leonbojic.shoppinglistserver.input.UserCredentials;
import leonbojic.shoppinglistserver.security.JwtService;
import leonbojic.shoppinglistserver.service.UserService;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user credentials");
        }

        String token = jwtService.generateToken(userDetailsService.loadUserByUsername(credentials.username()));

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCredentials credentials) {
        try {
            userService.createUser(credentials.username(), credentials.password());
        } catch (InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok("Successfully registered");
    }
}
