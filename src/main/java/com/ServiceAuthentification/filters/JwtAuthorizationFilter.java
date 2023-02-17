/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServiceAuthentification.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author PC-Alex
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationToken=request.getHeader("Authorization");
        if(authorizationToken != null && authorizationToken.startsWith("Bearer ") ){
           
            try{
               String jwtToken=authorizationToken.substring(7);
              
               Algorithm Algo2=Algorithm.HMAC256("mysecret1234");
               JWTVerifier jwtVerifier=JWT.require(Algo2).build();
               DecodedJWT  decodeJwt =jwtVerifier.verify(jwtToken);
               
               String username=decodeJwt.getSubject();               
               String roles[]=decodeJwt.getClaim("role").asArray(String.class);
               
                            
               List<GrantedAuthority> authorities=new ArrayList<>();               
               for(String r:roles){
                   authorities.add(new SimpleGrantedAuthority(r));
                   
               }          
                          
               UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
                 
               //pour authentifier l'utilisateur
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               //pour laisser passer la requette
               filterChain.doFilter(request, response);
               
            }catch(Exception e){
                response.setHeader("ERROR MESSAGE:",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                
            }
            
        }else{
         filterChain.doFilter(request, response);
        }
   
        
    }
    
}
