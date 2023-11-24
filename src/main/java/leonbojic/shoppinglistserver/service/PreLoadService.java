package leonbojic.shoppinglistserver.service;

import leonbojic.shoppinglistserver.model.User;

public interface PreLoadService {
    void createUserHistory(User user);
}
