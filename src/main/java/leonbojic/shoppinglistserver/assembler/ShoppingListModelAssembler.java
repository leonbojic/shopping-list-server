package leonbojic.shoppinglistserver.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import leonbojic.shoppinglistserver.controller.ShoppingListController;
import leonbojic.shoppinglistserver.model.ShoppingList;
import leonbojic.shoppinglistserver.output.ShoppingListOutput;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ShoppingListModelAssembler
        implements RepresentationModelAssembler<ShoppingList, EntityModel<ShoppingListOutput>> {

    @Override
    public EntityModel<ShoppingListOutput> toModel(ShoppingList shoppingList) {

        ShoppingListOutput output = new ShoppingListOutput(shoppingList.getName(), shoppingList.getTimeBought());

        EntityModel<ShoppingListOutput> em = EntityModel.of(output);

        em.add(linkTo(methodOn(ShoppingListController.class).delete(null, shoppingList.getId()))
                .withRel("delete"));

        return em;
    }
}
