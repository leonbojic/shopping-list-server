package leonbojic.shoppinglistserver.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import leonbojic.shoppinglistserver.assembler.ProductModelAssembler;
import leonbojic.shoppinglistserver.controller.ProductController;
import leonbojic.shoppinglistserver.exception.ProductNotFoundException;
import leonbojic.shoppinglistserver.exception.ShoppingListNotFoundException;
import leonbojic.shoppinglistserver.input.ProductInput;
import leonbojic.shoppinglistserver.model.Product;
import leonbojic.shoppinglistserver.model.ShoppingList;
import leonbojic.shoppinglistserver.output.ProductOutput;
import leonbojic.shoppinglistserver.repository.ProductRepository;
import leonbojic.shoppinglistserver.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ShoppingListRepository shoppingListRepository;
    private final ProductRepository productRepository;

    private final ProductModelAssembler modelAssembler;

    @Override
    public URI create(String myUsername, Long listId, ProductInput input) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndOwnerUsernameWithProducts(listId, myUsername)
                .orElseThrow(() -> new ShoppingListNotFoundException(listId));

        Product newProduct = new Product(input.getName(), input.getAmount(), input.getPrice(), input.getCategory(),
                shoppingList);
        shoppingList.getProducts().add(newProduct);
        shoppingList = shoppingListRepository.save(shoppingList);

        return linkTo(methodOn(ProductController.class).one(null, listId, newProduct.getId())).withSelfRel().toUri();
    }

    @Override
    public EntityModel<ProductOutput> update(String myUsername, Long listId, Long productId, ProductInput input) {

        Product product = productRepository.findByIdAndListIdAndUsername(productId, listId, myUsername)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (input.getAmount() != null) {
            product.setAmount(input.getAmount());
        }
        if (input.getCategory() != null) {
            product.setCategory(input.getCategory());
        }
        if (input.getName() != null) {
            product.setName(input.getName());
        }
        if (input.getPrice() != null) {
            product.setPrice(input.getPrice());
        }

        product = productRepository.save(product);

        return modelAssembler.toModel(product);
    }

    @Override
    public void delete(String myUsername, Long listId, Long productId) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndOwnerUsernameWithProducts(listId, myUsername)
                .orElseThrow(() -> new ShoppingListNotFoundException(listId));

        if (shoppingList.getProducts().removeIf(product -> product.getId().equals(productId))) {
            shoppingListRepository.save(shoppingList);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Override
    public CollectionModel<EntityModel<ProductOutput>> loadByShoppingList(String myUsername, Long listId) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndOwnerUsernameWithProducts(listId, myUsername)
                .orElseThrow(() -> new ShoppingListNotFoundException(listId));

        CollectionModel<EntityModel<ProductOutput>> cm = CollectionModel.of(
                shoppingList.getProducts().stream().map(product -> modelAssembler.toModel(product))
                        .collect(Collectors.toList()));

        cm.add(linkTo(methodOn(ProductController.class).create(null, listId, null)).withRel("create"));

        return cm;
    }

    @Override
    public EntityModel<ProductOutput> loadOne(String myUsername, Long listId, Long productId) {

        Product product = productRepository.findByIdAndListIdAndUsername(productId, listId, myUsername)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return modelAssembler.toModel(product);
    }

}
