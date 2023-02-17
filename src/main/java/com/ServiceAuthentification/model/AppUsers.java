/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author PC-Alex
 */
@Data
@Entity(name="User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUsers implements Serializable{
    
    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @ManyToMany(fetch=FetchType.EAGER)
    private List<AppRoles> appRoles=new ArrayList<>();

    
}
