package com.disconnected.marketplace.entity; 
 
import javax.persistence.*; 
import javax.validation.constraints.Email; 
import javax.validation.constraints.NotBlank; 
import javax.validation.constraints.Size; 
 
@Entity 
public class User { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    @NotBlank(message = "Name is mandatory") 
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") 
    private String name; 
 
    @Email(message = "Email should be valid") 
    @NotBlank(message = "Email is mandatory") 
    private String email; 
 
    @NotBlank(message = "Role is mandatory") 
    private String role; 
 
    // Getters and Setters 
 
    public Long getId() { 
        return id; 
    } 
 
    public void setId(Long id) { 
        this.id = id; 
    } 
 
    public String getName() { 
        return name; 
    } 
 
    public void setName(String name) { 
        this.name = name; 
    } 
 
    public String getEmail() { 
        return email; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    } 
 
    public String getRole() { 
        return role; 
    } 
 
    public void setRole(String role) { 
        this.role = role; 
    } 
}