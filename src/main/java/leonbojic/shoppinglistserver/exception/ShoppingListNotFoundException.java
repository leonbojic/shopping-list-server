package leonbojic.shoppinglistserver.exception;

public class ShoppingListNotFoundException extends RuntimeException {
    public ShoppingListNotFoundException(Long id) {
        super("Shopping List not found: " + id);
    }
}
