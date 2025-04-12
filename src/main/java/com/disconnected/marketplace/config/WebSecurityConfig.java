package com.disconnected.marketplace.config; 
 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
import org.springframework.security.crypto.password.NoOpPasswordEncoder; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
@Configuration 
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 
 
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class); 
 
    @Override 
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
        try { 
            auth.inMemoryAuthentication() 
                .withUser("user") 
                .password("password") 
                .roles("USER"); 
            logger.info("In-memory authentication configured successfully."); 
        } catch (Exception e) { 
            logger.error("Error configuring in-memory authentication: ", e); 
            throw e; 
        } 
    } 
 
    @Bean 
    @Override 
    public AuthenticationManager authenticationManagerBean() throws Exception { 
        try { 
            AuthenticationManager authenticationManager = super.authenticationManagerBean(); 
            logger.info("AuthenticationManager bean created successfully."); 
            return authenticationManager; 
        } catch (Exception e) { 
            logger.error("Error creating AuthenticationManager bean: ", e); 
            throw e; 
        } 
    } 
 
    @Bean 
    public static NoOpPasswordEncoder passwordEncoder() { 
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); 
    } 
}