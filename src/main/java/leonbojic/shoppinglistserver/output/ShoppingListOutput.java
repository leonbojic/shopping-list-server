package leonbojic.shoppinglistserver.output;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingListOutput {
    private Long id;
    private String name;
    private LocalDate timeBought;
}
