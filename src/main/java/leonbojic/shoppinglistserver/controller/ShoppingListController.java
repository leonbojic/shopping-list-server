package leonbojic.shoppinglistserver.controller;

import java.security.Principal;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leonbojic.shoppinglistserver.input.ShoppingListInput;
import leonbojic.shoppinglistserver.output.ShoppingListOutput;
import leonbojic.shoppinglistserver.service.ShoppingListService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/list")
@AllArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping("/create")
    public ResponseEntity<?> create(Principal principal, @RequestBody ShoppingListInput input) {

        return ResponseEntity.created(shoppingListService.create(principal.getName(), input)).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(Principal principal, @PathVariable Long id) {
        shoppingListService.delete(principal.getName(), id);

        return ResponseEntity.status(204).body("Shopping list successfully deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ShoppingListOutput>> one(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(shoppingListService.loadOne(principal.getName(), id));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ShoppingListOutput>>> all(Principal principal) {
        return ResponseEntity.ok(shoppingListService.loadAll(principal.getName()));
    }
}
