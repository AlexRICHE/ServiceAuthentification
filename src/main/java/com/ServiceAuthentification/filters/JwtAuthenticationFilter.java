/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.filters;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author PC-Alex
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
        

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      
        System.out.println("attemptAuthentication");
        
         // Récupération du nom d'utilisateur et du mot de passe depuis la requête
           String username = request.getParameter("username");
           String password = request.getParameter("password");
         
           System.out.println(username);
           System.out.println(password);
         
          // Création d'une instance d'UsernamePasswordAuthenticationToken
         UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
         
         // Authentification de l'utilisateur en utilisant un AuthenticationManager
            return authenticationManager.authenticate(authenticationToken);
          
          
    }           
        
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
             
             System.out.println("successfulAuthentication");
             User user=(User)authResult.getPrincipal(); 
             
             Algorithm algo=Algorithm.HMAC256("mysecret1234"); 
             
             String jwtAccesToken= JWT.create()
                              .withSubject(user.getUsername())
                              .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                              .withIssuer(request.getRequestURL().toString())
                              .withClaim("role", user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                              .sign(algo);              
                           
           // response.setHeader(jwtAccesToken, jwtAccesToken);
            String jwtRefreshToken= JWT.create()
                              .withSubject(user.getUsername())
                              .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                              .withIssuer(request.getRequestURL().toString())
                              .sign(algo); 
            
            Map<String,String> idToken=new HashMap<>();
            idToken.put("jwtAccesToken", jwtAccesToken);
            idToken.put("jwtRefreshToken", jwtRefreshToken);
            
            response.setContentType("apllication/json");
            new ObjectMapper().writeValue(response.getOutputStream(),idToken );
             
            System.out.println(idToken);
            //super.successfulAuthentication(request, response, chain, authResult);
           // Récupération du nom d'utilisateur connecté
              //String username = authResult.getName();
    
    
    }    
    
    
    
}
