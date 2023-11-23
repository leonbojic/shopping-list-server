package leonbojic.shoppinglistserver.controller;

import java.net.URI;
import java.security.Principal;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leonbojic.shoppinglistserver.input.ProductInput;
import leonbojic.shoppinglistserver.output.ProductOutput;
import leonbojic.shoppinglistserver.service.ProductService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/list/{listId}/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<EntityModel<ProductOutput>> one(
            Principal principal,
            @PathVariable Long listId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.loadOne(principal.getName(), listId, productId));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductOutput>>> allByShoppingList(
            Principal principal,
            @PathVariable Long listId) {
        return ResponseEntity.ok(productService.loadByShoppingList(principal.getName(), listId));
    }

    @PostMapping("/create")
    public ResponseEntity<URI> create(
            Principal principal,
            @PathVariable Long listId,
            @RequestBody ProductInput input) {
                
        return ResponseEntity.status(201).body(productService.create(principal.getName(), listId, input));
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<EntityModel<ProductOutput>> update(
            Principal principal,
            @PathVariable Long listId,
            @PathVariable Long productId,
            @RequestBody ProductInput input) {

        return ResponseEntity.ok(productService.update(principal.getName(), listId, productId, input));
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<?> delete(
            Principal principal,
            @PathVariable Long listId,
            @PathVariable Long productId) {
        productService.delete(principal.getName(), listId, productId);
        return ResponseEntity.noContent().build();
    }
}
