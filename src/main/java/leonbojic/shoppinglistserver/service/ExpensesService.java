package leonbojic.shoppinglistserver.service;

import java.util.Map;

import leonbojic.shoppinglistserver.enums.ProductCategory;

public interface ExpensesService {
    Map<ProductCategory, Integer> getExpenses(String myUsername, Integer month, Integer year);

}