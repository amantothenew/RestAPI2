package com.restfull.api.RestAPI2.controller;

import com.restfull.api.RestAPI2.entity.User;
import com.restfull.api.RestAPI2.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
public class UserController {
    private UserService service;

    private MessageSource messageSource;

    public UserController(MessageSource messageSource, UserService service) {
        this.messageSource = messageSource;
        this.service = service;
    }



    @GetMapping(value = "/users",
            consumes = { "application/json", "application/xml" })
    public List<User> getAllUsers() {
        return service.getAllUser();
    }

    @PostMapping(value = "/users",
            consumes = { "application/json", "application/xml" })
    public ResponseEntity<Void> getAllEmployees(@RequestBody User user) {
        User save = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
