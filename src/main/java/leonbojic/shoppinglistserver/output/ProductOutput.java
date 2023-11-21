package leonbojic.shoppinglistserver.output;

import leonbojic.shoppinglistserver.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOutput {
    private String name;
    private Integer price;
    private Integer amount;
    private ProductCategory category;
}
