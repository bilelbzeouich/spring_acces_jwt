package com.app.rdv.security.service;



import com.app.rdv.security.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IServiceAuthentication iServiceAuthentication;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= iServiceAuthentication.LoadUserByUserName(username);
        if(appUser==null) throw new UsernameNotFoundException("User with " + username + " does not exist");
        String[] roles = appUser.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);

        return User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
    }
}
