package com.app.rdv.security.controller;

import com.app.rdv.security.entities.AppUser;
import com.app.rdv.security.service.IServiceAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    IServiceAuthentication iServiceAuthentication;

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser appUser = iServiceAuthentication.LoadUserByUserName(username);
        if (appUser != null) {
            return ResponseEntity.ok(appUser);
        }
        return ResponseEntity.notFound().build();
    }
}
