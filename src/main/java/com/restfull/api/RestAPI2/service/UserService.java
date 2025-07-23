package com.restfull.api.RestAPI2.service;

import com.restfull.api.RestAPI2.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {
    private static List<User> users = new ArrayList<>();
    private static Integer userCounter = 0;

    static {
        users.add(new User(++userCounter, "Aman Pal", 22, "Aman22"));
        users.add(new User(++userCounter, "Ravi Gupta", 21, "Aman22"));
        users.add(new User(++userCounter, "Utkarsh Pal", 22, "Aman22"));
    }

    public List<User> getAllUser() {
        return users;
    }

    public User save(User user){
        user.setId(++userCounter);
        users.add(user);
        return user;
    }

    public User deleteById(int id) {
        User employee = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);

        if(employee==null){
            return null;
        }

        users.remove(employee);
        return employee;
    }
}