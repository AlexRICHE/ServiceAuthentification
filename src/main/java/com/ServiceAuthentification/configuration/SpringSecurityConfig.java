package com.ServiceAuthentification.configuration;

import com.ServiceAuthentification.filters.JwtAuthenticationFilter;
import com.ServiceAuthentification.filters.JwtAuthorizationFilter;
import com.ServiceAuthentification.model.AppUsers;
import com.ServiceAuthentification.service.AccountService;
import com.ServiceAuthentification.service.UserDetailsServiceImpl;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author PC-Alex
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
     public UserDetailsServiceImpl userDetailsServiceImpl;

    public SpringSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
     
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
                
         auth.userDetailsService(userDetailsServiceImpl);
       
   }                       
      
    @Override
    public void configure(HttpSecurity http) throws Exception{
        
         http.csrf().disable();
        
        //pour desactiver le csrf pour la page h2 console
         //http.csrf().ignoringAntMatchers("/h2-console/**");
         
        //pour desactiver la protection contre les frames
        http.headers().frameOptions().disable();
        
        //pour indiquer a spring security vous n'utiliser pas les session
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
         //http.authorizeRequests().anyRequest().permitAll();
         //http.formLogin();
        
         http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
         //http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveUser/**").hasAuthority("ADMIN");
         //http.authorizeRequests().antMatchers(HttpMethod.GET,"/findAllUser/**").hasAuthority("USER");
         http.authorizeRequests().anyRequest().authenticated();
         
         
         http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
         http.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
         
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
   
}               
       
   
    

