package leonbojic.shoppinglistserver.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import leonbojic.shoppinglistserver.exception.InvalidRequestException;
import leonbojic.shoppinglistserver.model.User;
import leonbojic.shoppinglistserver.repository.UserRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void createUser(String username, String password) {
        if (password == null || password.isBlank()) {
            throw new InvalidRequestException("Invalid password");
        }

        if (username == null || username.isBlank()) {
            throw new InvalidRequestException("Invalid username");
        }

        if (userRepository.existsByUsername(username)) {
            throw new InvalidRequestException("Username is already in use: " + username);
        }

        User user = new User();
        user.setUsername(username);
        user.setRoles("ROLE_USER");
        user.setPassword(encoder.encode(password));

        userRepository.save(user);
    }
}
