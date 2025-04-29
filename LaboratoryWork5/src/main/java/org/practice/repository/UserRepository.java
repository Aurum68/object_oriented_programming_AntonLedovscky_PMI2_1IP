package org.practice.repository;

import org.practice.record.User;

import java.util.List;

public class UserRepository extends DataRepository<User> implements IUserRepository{

    public UserRepository(String fileName, Class<User> clazz) {
        super(fileName, clazz);
    }

    @Override
    public User getByLogin(String login) {
        List<User> users = getAll();
        for (User user : users){
            if (user.login().equals(login)){
                return user;
            }
        }
        return null;
    }
}
