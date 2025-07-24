package com.restfull.api.RestAPI2.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.restfull.api.RestAPI2.entity.AbstractUser;
import com.restfull.api.RestAPI2.entity.User;
import com.restfull.api.RestAPI2.service.AbstractUserService;
import com.restfull.api.RestAPI2.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//@Hidden
@RestController
public class UserController {
    private UserService service;

    private AbstractUserService absService;

    private MessageSource messageSource;

    public UserController(MessageSource messageSource, UserService service, AbstractUserService absService) {
        this.messageSource = messageSource;
        this.service = service;
        this.absService = absService;
    }

    @GetMapping(value = "/users",
            produces = { "application/json", "application/xml" })
    public List<User> getAllUsers() {
        return service.getAllUser();
    }

    @Parameter
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


    @GetMapping("/versioning/v1/users")
    public List<AbstractUser> getAbsUsersUrlVersioning() {
        return absService.findAll();
    }

    @GetMapping("/versioning/v2/users")
    public List<User> getUsersUrlVersioning() {
        return service.getAllUser();
    }

    @GetMapping(path = "/versioning/users", params = "version=1")
    public List<AbstractUser> getAbsUsersParamVersioning() {
        return absService.findAll();
    }

    @GetMapping(path = "/versioning/users", params = "version=2")
    public List<User> getUsersParamVersioning() {
        return service.getAllUser();
    }

    @GetMapping(path = "/versioning/users/headers", headers = "X-API-VERSION=1")
    public List<AbstractUser> getAbsUsersHeaderVersioning() {
        return absService.findAll();
    }

    @GetMapping(path = "/versioning/users/headers", headers = "X-API-VERSION=2")
    public List<User> getUsersHeaderVersioning() {
        return service.getAllUser();
    }

    @GetMapping(path = "/versioning/users/media", produces = "application/vnd.company.app-v1+json")
    public List<AbstractUser> getAbsUsersMediaVersioning() {
        return absService.findAll();
    }

    @GetMapping(path = "/versioning/users/media", produces = "application/vnd.company.app-v2+json")
    public List<User> getUsersMediaVersioning() {
        return service.getAllUser();
    }

    @GetMapping("/hateoas/users/{id}")
    public EntityModel<User> getUsersMediaVersioning(@PathVariable int id) {
        User user = service.findOne(id);
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).getAllUsers());
        entityModel.add(webMvcLinkBuilder.withRel("all-users"));
        return entityModel;
    }



}
