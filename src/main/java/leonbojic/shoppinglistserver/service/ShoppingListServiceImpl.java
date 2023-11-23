package leonbojic.shoppinglistserver.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import leonbojic.shoppinglistserver.assembler.ShoppingListModelAssembler;
import leonbojic.shoppinglistserver.controller.ShoppingListController;
import leonbojic.shoppinglistserver.exception.ShoppingListNotFoundException;
import leonbojic.shoppinglistserver.exception.UserNotFoundException;
import leonbojic.shoppinglistserver.input.EditListInput;
import leonbojic.shoppinglistserver.input.ProductInput;
import leonbojic.shoppinglistserver.input.ShoppingListInput;
import leonbojic.shoppinglistserver.model.Product;
import leonbojic.shoppinglistserver.model.ShoppingList;
import leonbojic.shoppinglistserver.model.User;
import leonbojic.shoppinglistserver.output.ShoppingListOutput;
import leonbojic.shoppinglistserver.repository.ShoppingListRepository;
import leonbojic.shoppinglistserver.repository.UserRepository;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@AllArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;

    private final ShoppingListModelAssembler modelAssembler;

    @Override
    public URI create(String myUsername, ShoppingListInput input) {

        LocalDate dateNow = LocalDate.now();

        User user = userRepository.findByUsername(myUsername)
                .orElseThrow(() -> new UserNotFoundException(myUsername));

        ShoppingList shoppingList = new ShoppingList(input.getName(), dateNow, user);

        for (ProductInput product : input.getProducts()) {
            shoppingList.getProducts().add(new Product(product.getName(), product.getAmount(), product.getPrice(),
                    product.getCategory(), shoppingList));
        }

        user.getShoppingLists().add(shoppingList);

        shoppingList = shoppingListRepository.save(shoppingList);
        userRepository.save(user);

        return linkTo(methodOn(ShoppingListController.class).one(null, shoppingList.getId())).withSelfRel().toUri();
    }

    @Override
    public void delete(String myUsername, Long id) {
        User user = userRepository.findByUsernameWithShoppingLists(myUsername)
                .orElseThrow(() -> new UserNotFoundException(myUsername));

        if (user.getShoppingLists().removeIf(sl -> sl.getId().equals(id))) {
            userRepository.save(user);
            shoppingListRepository.deleteById(id);
        } else {
            throw new ShoppingListNotFoundException(id);
        }
    }

    @Override
    public CollectionModel<EntityModel<ShoppingListOutput>> loadAll(String myUsername) {

        CollectionModel<EntityModel<ShoppingListOutput>> cm = CollectionModel
                .of(shoppingListRepository.findByOwnerUsername(myUsername)
                        .stream()
                        .map(sl -> modelAssembler.toModel(sl))
                        .collect(Collectors.toList()));

        cm.add(linkTo(methodOn(ShoppingListController.class).create(null, null)).withRel("create"));

        return cm;
    }

    @Override
    public EntityModel<ShoppingListOutput> loadOne(String myUsername, Long id) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndOwnerUsername(id, myUsername)
                .orElseThrow(() -> new ShoppingListNotFoundException(id));

        return modelAssembler.toModel(shoppingList);
    }

    @Override
    public EntityModel<ShoppingListOutput> update(String myUsername, Long id, EditListInput input) {
        ShoppingList shoppingList = shoppingListRepository.findByIdAndOwnerUsername(id, myUsername)
                .orElseThrow(() -> new ShoppingListNotFoundException(id));

        shoppingList.setTimeBought(input.getDate());
        shoppingList.setName(input.getName());

        shoppingList = shoppingListRepository.save(shoppingList);

        return modelAssembler.toModel(shoppingList);
    }

}
