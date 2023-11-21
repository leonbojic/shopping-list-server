package leonbojic.shoppinglistserver.input;

import java.util.List;

import lombok.Getter;

@Getter
public class ShoppingListInput {
    private String name;

    private List<ProductInput> products;
}
