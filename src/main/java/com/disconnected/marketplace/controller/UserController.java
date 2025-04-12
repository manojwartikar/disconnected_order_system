package com.disconnected.marketplace.controller; 
 
import com.disconnected.marketplace.entity.User; 
import com.disconnected.marketplace.service.UserService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.*; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice; 
import org.springframework.http.HttpStatus; 
import org.springframework.validation.FieldError; 
 
import java.util.List; 
import java.util.Optional; 
import java.util.HashMap; 
import java.util.Map; 
 
@RestController 
@RequestMapping("/api/users") 
@Validated 
public class UserController { 
 
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
 
    @Autowired 
    private UserService userService; 
 
    @GetMapping 
    public List<User> getAllUsers() { 
        logger.debug("Request to get all users"); 
        return userService.findAll(); 
    } 
 
    @GetMapping("/{id}") 
    public ResponseEntity<User> getUserById(@PathVariable Long id) { 
        logger.debug("Request to get user by ID: {}", id); 
        Optional<User> user = userService.findById(id); 
        if (user.isPresent()) { 
            logger.debug("User found: {}", user.get()); 
            return ResponseEntity.ok(user.get()); 
        } else { 
            logger.warn("User not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
    @PostMapping 
    public ResponseEntity<User> createUser(@RequestBody @Validated User user) { 
        try { 
            logger.debug("Request to create user: {}", user); 
            User createdUser = userService.save(user); 
            logger.debug("User created successfully: {}", createdUser); 
            return ResponseEntity.ok(createdUser); 
        } catch (Exception e) { 
            logger.error("Error creating user: ", e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
        } 
    } 
 
    @PutMapping("/{id}") 
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Validated User user) { 
        logger.debug("Request to update user with ID: {}", id); 
        if (!userService.findById(id).isPresent()) { 
            logger.warn("User not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            user.setId(id); 
            User updatedUser = userService.save(user); 
            logger.debug("User updated successfully: {}", updatedUser); 
            return ResponseEntity.ok(updatedUser); 
        } catch (Exception e) { 
            logger.error("Error updating user with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
        } 
    } 
 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) { 
        logger.debug("Request to delete user with ID: {}", id); 
        if (!userService.findById(id).isPresent()) { 
            logger.warn("User not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            userService.deleteById(id); 
            logger.debug("User deleted successfully with ID: {}", id); 
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) { 
            logger.error("Error deleting user with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
} 
 
@RestControllerAdvice 
class GlobalExceptionHandler { 
 
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); 
 
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) { 
        Map<String, String> errors = new HashMap<>(); 
        ex.getBindingResult().getAllErrors().forEach((error) -> { 
            String fieldName = ((FieldError) error).getField(); 
            String errorMessage = error.getDefaultMessage(); 
            errors.put(fieldName, errorMessage); 
        }); 
        logger.error("Validation error: {}", errors); 
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); 
    } 
 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleAllExceptions(Exception ex) { 
        logger.error("An error occurred: ", ex); 
        return new ResponseEntity<>("An internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
}