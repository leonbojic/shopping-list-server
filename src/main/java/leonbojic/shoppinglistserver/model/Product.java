package leonbojic.shoppinglistserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import leonbojic.shoppinglistserver.enums.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    private @Id @GeneratedValue Long id;

    private String name;
    private Integer amount;
    private Integer price;
    private ProductCategory category;

    @ManyToOne
    private ShoppingList shoppingList;

    public Product(String name, Integer amount, Integer price, ProductCategory category, ShoppingList shoppingList) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.shoppingList = shoppingList;
    }
}
