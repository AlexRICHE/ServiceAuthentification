/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.controller;

import com.ServiceAuthentification.model.AppRoles;
import com.ServiceAuthentification.model.AppUsers;
import com.ServiceAuthentification.model.AddRolesToUsersForm;
import com.ServiceAuthentification.service.AccountService;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PC-Alex
 */
@RestController
@RequestMapping("accountService")
public class AccountController {
    
    @Autowired
    public AccountService accountService;
    
    
    //methode pour user
    @PostMapping("/saveUser")//localhost:8080/accountService/saveUser
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUsers enregistrerUser(@RequestBody AppUsers appUsers){
        return accountService.enregistrerUser(appUsers);
        
    }       
    @GetMapping("/findUser/{username}")//localhost:8080/accountService/findUser/user1
    public AppUsers rechercherUser(@PathVariable("username")String username){
        return accountService.rechercherUser(username);
    }  
    
    @GetMapping("/findAllUser")//localhost:8080/accountService/findAllUser
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUsers> listerUser(){
        return accountService.listerUser();
    }
    
    //methode pour role
    @PostMapping("/saveRole")//localhost:8080/accountService/saveRole
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRoles enregistrerROle(@RequestBody AppRoles appRoles){
        return accountService.enregistrerRole(appRoles);
        
    }
    @GetMapping("/findRole/{rolename}")//localhost:8080/accountService/findRole/ADMIN
    @PostAuthorize("hasAuthority('USER')")
    public AppRoles rechercherRole(@PathVariable("rolename")String rolename){
        return accountService.rechercherRole(rolename);
    }  
    
     @PostMapping("/AddRolesToUsers")//localhost:8080/accountService/AddRolesToUsers
     //@PostAuthorize("hasAuthority('ADMIN')")
    public AddRolesToUsersForm AddRolesToUsers(@RequestBody AddRolesToUsersForm addRolesToUsersForm){
         accountService.AddRolesToUsers(addRolesToUsersForm.getUsername(),addRolesToUsersForm.getRolename());
        return addRolesToUsersForm; 
    }    
    
    
   
       
}
