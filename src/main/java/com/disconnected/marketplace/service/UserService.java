package com.disconnected.marketplace.service; 
 
import com.disconnected.marketplace.entity.User; 
import com.disconnected.marketplace.repository.UserRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import javax.validation.Valid; 
import java.util.List; 
import java.util.Optional; 
 
@Service 
public class UserService { 
 
    private static final Logger logger = LoggerFactory.getLogger(UserService.class); 
 
    @Autowired 
    private UserRepository userRepository; 
 
    public List<User> findAll() { 
        logger.debug("Fetching all users from the database."); 
        return userRepository.findAll(); 
    } 
 
    public Optional<User> findById(Long id) { 
        logger.debug("Fetching user with ID: {}", id); 
        return userRepository.findById(id); 
    } 
 
    public User save(@Valid User user) { 
        try { 
            logger.debug("Saving user: {}", user); 
            return userRepository.save(user); 
        } catch (Exception e) { 
            logger.error("Error saving user: ", e); 
            throw e; 
        } 
    } 
 
    public void deleteById(Long id) { 
        try { 
            logger.debug("Deleting user with ID: {}", id); 
            userRepository.deleteById(id); 
        } catch (Exception e) { 
            logger.error("Error deleting user with ID: {}", id, e); 
            throw e; 
        } 
    } 
 
    // Additional business logic can be added here 
} 