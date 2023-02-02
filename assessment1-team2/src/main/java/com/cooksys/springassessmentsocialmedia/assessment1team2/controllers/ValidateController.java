package com.cooksys.springassessmentsocialmedia.assessment1team2.controllers;

import com.cooksys.springassessmentsocialmedia.assessment1team2.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/username/exists/@{username}")
    public boolean usernameExists(@PathVariable String username) {
        return validateService.usernameExists(username);
    }

    @GetMapping("/tag/exists/{label}")
    public boolean hashtagExists(@PathVariable String label) {
        return validateService.hashtagExists(label);
    }

}
