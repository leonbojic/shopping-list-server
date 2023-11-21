package leonbojic.shoppinglistserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import leonbojic.shoppinglistserver.model.ShoppingList;


public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    @Query("SELECT s FROM ShoppingList s " +
            "JOIN s.owner o " +
            "WHERE s.id = :id " +
            "AND o.username = :username")
    Optional<ShoppingList> findShoppingListByIdAndOwnerUsername(
            @Param("id") Long id,
            @Param("username") String username);

    
    List<ShoppingList> findByOwnerUsername(String username);
}
