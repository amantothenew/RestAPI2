package com.restfull.api.RestAPI2.service;

import com.restfull.api.RestAPI2.entity.AbstractUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbstractUserService {

    private static List<AbstractUser> absUsers = new ArrayList<>();
    private static Integer userCounter = 0;

    static {
        absUsers.add(new AbstractUser(++userCounter, "Aman Pal"));
    }

    public List<AbstractUser> findAll() {
        return absUsers;
    }
}
