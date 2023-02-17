package com.ServiceAuthentification.service;

import com.ServiceAuthentification.model.AddRolesToUsersForm;
import com.ServiceAuthentification.model.AppRoles;
import com.ServiceAuthentification.model.AppUsers;
import com.ServiceAuthentification.repository.AppRolesRepository;
import com.ServiceAuthentification.repository.AppUsersRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC-Alex
 */
@Service
@Transactional
public class AccountService {
    
    @Autowired
    private AppUsersRepository appUsersRepository;
    
    @Autowired
    private AppRolesRepository appRolesRepository;
    
    //methode Users
    
    public AppUsers enregistrerUser(AppUsers appUsers){
        String password=appUsers.getPassword();
        appUsers.setPassword(passwordEncoder().encode(password));
        return appUsersRepository.save(appUsers);
        
    }
    public AppUsers rechercherUser( String username){
        return appUsersRepository.findUsersByUsername(username);
    }
    public List<AppUsers> listerUser(){
         return appUsersRepository.findAll();
    }
    
    //methode Roles
    public AppRoles enregistrerRole(AppRoles appRoles){
      return appRolesRepository.save(appRoles);
   }

    public AppRoles rechercherRole(String rolename){
       return appRolesRepository.findRolesByRolename(rolename);
   }
     
   //methode Users Roles
    public void  AddRolesToUsers(String username, String rolename){
       AppUsers appUsers = appUsersRepository.findUsersByUsername(username);
       AppRoles appRoles = appRolesRepository.findRolesByRolename(rolename);
       
       appUsers.getAppRoles().add(appRoles);
    
      
   }
   
   // pour encoder le mot de pass
   @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
        
   
}
