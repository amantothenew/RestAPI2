package com.restfull.api.RestAPI2.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.restfull.api.RestAPI2.entity.User;
import com.restfull.api.RestAPI2.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
            produces = { "application/json", "application/xml" })
    public List<User> getAllUsers() {
        return service.getAllUser();
    }

    @PostMapping(value = "/users",
            consumes = { "application/json", "application/xml" })
    public User getAllEmployees(@RequestBody User user) {
        User save = service.save(user);
        return save;
    }

    @PostMapping(value = "/users-dynamic-filtering",
            consumes = { "application/json", "application/xml" })
    public MappingJacksonValue getAllEmployeesWithDynamicFiltering(@RequestBody User user) {
        User save = service.save(user);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(save);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "age");
        FilterProvider filters = new SimpleFilterProvider().addFilter("User", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @DeleteMapping("/users/{id}")
    public User deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }
}
