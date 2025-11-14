package com.app.rdv.security.service;

import com.app.rdv.security.entities.AppRole;
import com.app.rdv.security.entities.AppUser;
import com.app.rdv.security.repository.AppRoleRepository;
import com.app.rdv.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuthentication implements IServiceAuthentication{

    final AppUserRepository appUserRepository;
    final AppRoleRepository appRoleRepository;
    PasswordEncoder encoder= new BCryptPasswordEncoder();

    /*public ServiceAuthentication(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }*/

    @Override
    public AppUser createAppUser(AppUser appUser) {
        AppUser appUser1 = appUserRepository.findByUsername(appUser.getUsername());
        if(appUser1==null){
            appUser.setPassword(encoder.encode(appUser.getPassword()));
            return appUserRepository.save(appUser);
        }
        return null;
    }

    @Override
    public AppRole createAppRole(AppRole appRole) {
        AppRole appRole1 = appRoleRepository.findByRole(appRole.getRole());
        if(appRole1==null)
            return appRoleRepository.save(appRole);
        return null;
    }

    @Override
    public Boolean addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRole(role);
        if(appUser!=null && !appUser.getRoles().contains(appRole)){
            appUser.getRoles().add(appRole);
            appUserRepository.save(appUser);
            return true;
        }
        return false;
    }

    @Override
    public AppUser LoadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
