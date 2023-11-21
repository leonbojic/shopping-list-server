package leonbojic.shoppinglistserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import leonbojic.shoppinglistserver.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
