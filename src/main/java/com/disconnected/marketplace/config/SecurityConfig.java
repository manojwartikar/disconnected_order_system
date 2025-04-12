package com.disconnected.marketplace.config; 
 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer; 
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
@Configuration 
@EnableWebSecurity 
@EnableResourceServer 
public class SecurityConfig extends ResourceServerConfigurerAdapter { 
 
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class); 
 
    @Override 
    public void configure(HttpSecurity http) throws Exception { 
        try { 
            http 
                .authorizeRequests() 
                .antMatchers("/api/public/**").permitAll() 
                .anyRequest().authenticated(); 
            logger.info("Security configuration applied successfully."); 
        } catch (Exception e) { 
            logger.error("Error configuring security: ", e); 
            throw e; 
        } 
    } 
} 