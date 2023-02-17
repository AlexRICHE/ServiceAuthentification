/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.repository;

import com.ServiceAuthentification.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PC-Alex
 */
public interface AppUsersRepository extends JpaRepository<AppUsers,Long>{
    
    public AppUsers findUsersByUsername(String username);
    
}
