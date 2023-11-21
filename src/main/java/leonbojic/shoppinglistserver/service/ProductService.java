package leonbojic.shoppinglistserver.service;

import java.net.URI;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import leonbojic.shoppinglistserver.input.ProductInput;
import leonbojic.shoppinglistserver.output.ProductOutput;

public interface ProductService {

    URI create(String myUsername, Long listId, ProductInput input);

    void delete(String myUsername, Long listId, Long productId);

    EntityModel<ProductOutput> update(String myUsername, Long listId, Long productId, ProductInput input);

    EntityModel<ProductOutput> loadOne(String myUsername, Long listId, Long productId);

    CollectionModel<EntityModel<ProductOutput>> loadByShoppingList(String myUsername, Long listId);
}
