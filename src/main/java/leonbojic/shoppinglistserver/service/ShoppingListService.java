package leonbojic.shoppinglistserver.service;

import java.net.URI;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import leonbojic.shoppinglistserver.input.EditListInput;
import leonbojic.shoppinglistserver.input.ShoppingListInput;
import leonbojic.shoppinglistserver.output.ShoppingListOutput;


public interface ShoppingListService {

    URI create(String myUsername, ShoppingListInput input);

    void delete(String myUsername, Long id);

    EntityModel<ShoppingListOutput> loadOne(String myUsername, Long id);

    CollectionModel<EntityModel<ShoppingListOutput>> loadAll(String myUsername);

    EntityModel<ShoppingListOutput> update(String myUsername, Long id, EditListInput input);
}
