package leonbojic.shoppinglistserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import leonbojic.shoppinglistserver.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM ShoppingList sl " +
        "JOIN sl.products p " +
        "WHERE p.id = :productId "+
        "AND sl.id = :listId " +
        "AND sl.owner.username = :username ")
    Optional<Product> findByIdAndListIdAndUsername(
        @Param("productId") Long productId,
        @Param("listId") Long listId,
        @Param("username") String username
    );
}
