package com.disconnected.marketplace.config; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer; 
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer; 
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter; 
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfigurer; 
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfigurer; 
import org.springframework.security.oauth2.provider.token.TokenStore; 
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore; 
import org.springframework.security.crypto.password.NoOpPasswordEncoder; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
@Configuration 
@EnableAuthorizationServer 
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter { 
 
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class); 
 
    @Autowired 
    private AuthenticationManager authenticationManager; 
 
    @Override 
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { 
        try { 
            clients.inMemory() 
                .withClient("client-id") 
                .secret("client-secret") 
                .authorizedGrantTypes("password", "refresh_token") 
                .scopes("read", "write") 
                .accessTokenValiditySeconds(3600) 
                .refreshTokenValiditySeconds(7200); 
            logger.info("Client details configured successfully."); 
        } catch (Exception e) { 
            logger.error("Error configuring client details: ", e); 
            throw e; 
        } 
    } 
 
    @Override 
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { 
        try { 
            endpoints 
                .tokenStore(tokenStore()) 
                .authenticationManager(authenticationManager); 
            logger.info("Authorization server endpoints configured successfully."); 
        } catch (Exception e) { 
            logger.error("Error configuring authorization server endpoints: ", e); 
            throw e; 
        } 
    } 
 
    @Bean 
    public TokenStore tokenStore() { 
        return new InMemoryTokenStore(); 
    } 
 
    @Override 
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception { 
        try { 
            security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()"); 
            logger.info("Authorization server security configured successfully."); 
        } catch (Exception e) { 
            logger.error("Error configuring authorization server security: ", e); 
            throw e; 
        } 
    } 
 
    @Bean 
    public static NoOpPasswordEncoder passwordEncoder() { 
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); 
    } 
}