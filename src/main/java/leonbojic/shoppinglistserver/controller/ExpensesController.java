package leonbojic.shoppinglistserver.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import leonbojic.shoppinglistserver.enums.ProductCategory;
import leonbojic.shoppinglistserver.service.ExpensesService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@AllArgsConstructor
public class ExpensesController {

    private final ExpensesService expensesService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<Map<ProductCategory, Integer>> getExpenses(
            Principal principal,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        
        return ResponseEntity.ok(expensesService.getExpenses(principal.getName(), month, year));
    }

}
