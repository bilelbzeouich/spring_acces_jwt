package com.app.rdv.security.service;

import com.app.rdv.security.entities.AppRole;
import com.app.rdv.security.entities.AppUser;

public interface IServiceAuthentication {
    public AppUser createAppUser(AppUser appUser);
    public AppRole createAppRole(AppRole appRole);
    public Boolean addRoleToUser(String username, String role);
    public AppUser LoadUserByUserName(String username);
}
