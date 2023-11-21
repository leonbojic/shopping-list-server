package leonbojic.shoppinglistserver.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingList {

    private @Id @GeneratedValue Long id;
    private String name;
    private LocalDate timeBought;

    @ManyToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public ShoppingList(){
        this.products = new ArrayList<>();
    }

    public ShoppingList(String name, LocalDate timeBought, User owner) {
        this();
        this.name = name;
        this.timeBought = timeBought;
        this.owner = owner;
    }
}
