package com.app.rdv.security.controller;

import com.app.rdv.security.dto.LoginRequest;
import com.app.rdv.security.dto.LoginResponse;
import com.app.rdv.security.entities.AppRole;
import com.app.rdv.security.entities.AppUser;
import com.app.rdv.security.service.IServiceAuthentication;
import com.app.rdv.security.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/register/")
public class AuthenticationController {

    IServiceAuthentication iServiceAuthentication;
    AuthenticationManager authenticationManager;
    UserDetailsService userDetailsService;
    JwtUtil jwtUtil;

    @PostMapping("appuser")
    public ResponseEntity<?> adduser(@RequestBody AppUser appUser) {
        AppUser appUser1 = iServiceAuthentication.createAppUser(appUser);
        if (appUser1 != null)
            return new ResponseEntity<>(appUser1, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("User exit with username " + appUser.getUsername(), HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("approle")
    public ResponseEntity<?> addrole(@RequestBody AppRole appRole) {

        AppRole appRole1 = iServiceAuthentication.createAppRole(appRole);
        if (appRole1 != null)
            return new ResponseEntity<>(appRole1, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Role" + appRole.getRole() + " exists", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("roletouser")
    public ResponseEntity<String> addRoleToUser(@RequestParam String username, @RequestParam String role) {
        if (iServiceAuthentication.addRoleToUser(username, role))
            return new ResponseEntity<>("Role " + role + " affected to " + username, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Role still affected " + role, HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
