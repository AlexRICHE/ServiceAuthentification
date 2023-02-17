/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.service;

import com.ServiceAuthentification.model.AppRoles;
import com.ServiceAuthentification.model.AppUsers;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC-Alex
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }
        

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
         AppUsers appUsers=accountService.rechercherUser(username);
                 Collection<GrantedAuthority> authorities=new ArrayList<>();
                 
                 appUsers.getAppRoles().forEach(r->{
                     authorities.add(new SimpleGrantedAuthority(r.getRolename()));
                 });
               
               /* 
                for(AppRoles r: appUsers.getAppRoles()){
                      authorities.add(new SimpleGrantedAuthority(r.getRolename()));
                }
               */
                                
                 return new User(appUsers.getUsername(),appUsers.getPassword(),authorities);
    }
    
    
}
