package leonbojic.shoppinglistserver.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Table(name = "users")
@Entity
@Getter
@Setter
@NamedEntityGraph(name = "User.shoppingLists", attributeNodes = @NamedAttributeNode("shoppingLists"))
public class User {
    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ShoppingList> shoppingLists;

    public User() {
        this.shoppingLists = new ArrayList<>();
    }
}
