package com.ServiceAuthentification;

import com.ServiceAuthentification.model.AppRoles;
import com.ServiceAuthentification.model.AppUsers;
import com.ServiceAuthentification.service.AccountService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class ServiceAuthentificationApplication implements CommandLineRunner{
    
    
   @Autowired
    public AccountService accountService;   

    public static void main(String[] args) {
		SpringApplication.run(ServiceAuthentificationApplication.class, args);
	}
    
    @Override
    public void run(String... args) throws Exception {
        
        
        accountService.enregistrerRole(new AppRoles(null,"USER"));
        accountService.enregistrerRole(new AppRoles(null,"ADMIN"));
        accountService.enregistrerRole(new AppRoles(null,"COSTUMER_MANAGER"));
        accountService.enregistrerRole(new AppRoles(null,"PRODUCT_NAMAGER"));
        accountService.enregistrerRole(new AppRoles(null,"BILL_MANAGER"));
        accountService.enregistrerRole(new AppRoles(null,"ADM"));
        
        accountService.enregistrerUser(new AppUsers(null,"user1","1234",new ArrayList<>()));
        accountService.enregistrerUser(new AppUsers(null,"admin","1234",new ArrayList<>()));
        accountService.enregistrerUser(new AppUsers(null,"user2","1234",new ArrayList<>()));
        accountService.enregistrerUser(new AppUsers(null,"user3","1234",new ArrayList<>()));
        accountService.enregistrerUser(new AppUsers(null,"user4","1234",new ArrayList<>()));
        accountService.enregistrerUser(new AppUsers(null,"alex","1234",new ArrayList<>()));
        
        accountService.AddRolesToUsers("user1","USER");
        accountService.AddRolesToUsers("admin","USER");
        accountService.AddRolesToUsers("admin","ADMIN");
        accountService.AddRolesToUsers("user2","USER");
        accountService.AddRolesToUsers("user2","COSTUMER_MANAGER");
        accountService.AddRolesToUsers("user3","USER");
        accountService.AddRolesToUsers("user3","PRODUCT_NAMAGER");
        accountService.AddRolesToUsers("user4","USER");
        accountService.AddRolesToUsers("user4","BILL_MANAGER");
        accountService.AddRolesToUsers("alex","USER");
        accountService.AddRolesToUsers("alex","ADM");
        
        
         
    }

}
