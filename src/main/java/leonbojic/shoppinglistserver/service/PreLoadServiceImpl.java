package leonbojic.shoppinglistserver.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import leonbojic.shoppinglistserver.model.Product;
import leonbojic.shoppinglistserver.model.ShoppingList;
import leonbojic.shoppinglistserver.model.User;
import leonbojic.shoppinglistserver.repository.ShoppingListRepository;
import leonbojic.shoppinglistserver.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PreLoadServiceImpl implements PreLoadService {

    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Override
    public void createUserHistory(User user) {
        List<ShoppingList> lists = readJsonFile();

        for (ShoppingList list : lists) {
            user.getShoppingLists().add(list);
            list.setOwner(user);
            for(Product product : list.getProducts()){
                product.setShoppingList(list);
            }
        }

        shoppingListRepository.saveAll(lists);
        userRepository.save(user);
    }

    private List<ShoppingList> readJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<ShoppingList> shoppingLists = null;

        try {
            Resource resource = new ClassPathResource("shoppingLists.json");
            File file = resource.getFile();
            shoppingLists = objectMapper.readValue(file, new TypeReference<List<ShoppingList>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shoppingLists;
    }

}
