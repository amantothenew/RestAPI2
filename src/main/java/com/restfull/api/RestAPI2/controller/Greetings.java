package com.restfull.api.RestAPI2.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class Greetings {

    private MessageSource messageSource;

    public Greetings(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello")
    public String greetUser(@RequestParam String username) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("greeting.message", new Object[]{username}, "Hello!", locale);    }
}
