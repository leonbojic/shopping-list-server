package leonbojic.shoppinglistserver.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import leonbojic.shoppinglistserver.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    @Query("SELECT s FROM ShoppingList s " +
            "JOIN s.owner o " +
            "WHERE s.id = :id " +
            "AND o.username = :username")
    Optional<ShoppingList> findByIdAndOwnerUsername(
            @Param("id") Long id,
            @Param("username") String username);

    @EntityGraph(value = "ShoppingList.products", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT s FROM ShoppingList s " +
            "JOIN s.owner o " +
            "WHERE s.id = :id " +
            "AND o.username = :username")
    Optional<ShoppingList> findByIdAndOwnerUsernameWithProducts(
            @Param("id") Long id,
            @Param("username") String username);

    List<ShoppingList> findByOwnerUsername(String username);

    @EntityGraph(value = "ShoppingList.products", type = EntityGraph.EntityGraphType.LOAD)
    List<ShoppingList> findByOwnerUsernameAndTimeBoughtBetween(
            String username,
            LocalDate startDate,
            LocalDate endDate);
}
