package leonbojic.shoppinglistserver.input;

import leonbojic.shoppinglistserver.enums.ProductCategory;
import lombok.Getter;

@Getter
public class ProductInput {
    private String name;
    private Integer price;
    private Integer amount;

    private ProductCategory category;
}
