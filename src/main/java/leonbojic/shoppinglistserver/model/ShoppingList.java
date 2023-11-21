package leonbojic.shoppinglistserver.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShoppingList {
    
    private @Id @GeneratedValue Long id;
    private String name;
    private LocalDate timeBought;

    @ManyToOne 
    private User owner;

    public ShoppingList(String name, LocalDate timeBought, User owner){
        this.name = name;
        this.timeBought = timeBought;
        this.owner = owner;
    }
}
