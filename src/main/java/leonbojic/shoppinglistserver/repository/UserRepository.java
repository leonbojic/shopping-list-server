package leonbojic.shoppinglistserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import leonbojic.shoppinglistserver.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @EntityGraph(value = "User.shoppingLists", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u " +
            "WHERE u.username = :username")
    Optional<User> findByUsernameWithShoppingLists(@Param("username") String username);
}
