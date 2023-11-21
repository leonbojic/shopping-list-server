package leonbojic.shoppinglistserver.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import leonbojic.shoppinglistserver.controller.ProductController;
import leonbojic.shoppinglistserver.model.Product;
import leonbojic.shoppinglistserver.output.ProductOutput;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<ProductOutput>> {

    @Override
    public EntityModel<ProductOutput> toModel(Product product) {

        ProductOutput output = new ProductOutput(product.getName(), product.getPrice(), product.getAmount(),
                product.getCategory());

        EntityModel<ProductOutput> em = EntityModel.of(output);

        em.add(linkTo(methodOn(ProductController.class).one(null, product.getShoppingList().getId(), product.getId()))
                .withSelfRel());

        em.add(linkTo(methodOn(ProductController.class).update(null, product.getShoppingList().getId(), product.getId(),
                null))
                .withRel("update"));

        em.add(linkTo(
                methodOn(ProductController.class).delete(null, product.getShoppingList().getId(), product.getId()))
                .withRel("delete"));

        return em;
    }
}
