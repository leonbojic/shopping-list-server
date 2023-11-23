package leonbojic.shoppinglistserver.service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import leonbojic.shoppinglistserver.enums.ProductCategory;
import leonbojic.shoppinglistserver.exception.InvalidRequestException;
import leonbojic.shoppinglistserver.model.Product;
import leonbojic.shoppinglistserver.model.ShoppingList;
import leonbojic.shoppinglistserver.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {

    private final ShoppingListRepository shoppingListRepository;

    @Override
    public Map<ProductCategory, Integer> getExpenses(String myUsername, Integer month, Integer year) {
        if (month < 1 || month > 12 || year < 0) {
            throw new InvalidRequestException("Invalid month or year");
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        List<ShoppingList> lists = shoppingListRepository.findByOwnerUsernameAndTimeBoughtBetween(myUsername, startDate, endDate);

        Map<ProductCategory, Integer> expenses = new EnumMap<>(ProductCategory.class);

        for(ShoppingList list : lists){
            for (Product product : list.getProducts()){
                ProductCategory category = product.getCategory();
                int expense = product.getPrice() * product.getAmount();
                expenses.put(category, expenses.getOrDefault(category, 0) + expense);
            }
        }

        return expenses;
    }
}
