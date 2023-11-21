package leonbojic.shoppinglistserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {
    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String roles;

    public User() {

    }
}
