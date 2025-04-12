package com.disconnected.marketplace.entity; 
 
import javax.persistence.*; 
import javax.validation.constraints.NotBlank; 
import javax.validation.constraints.Size; 
import javax.validation.constraints.Email; 
 
@Entity 
public class Supplier { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    @NotBlank(message = "Name is mandatory") 
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") 
    private String name; 
 
    @NotBlank(message = "Contact information is mandatory") 
    @Size(max = 255, message = "Contact information must be less than 255 characters") 
    private String contactInformation; 
 
    @Email(message = "Email should be valid") 
    @NotBlank(message = "Email is mandatory") 
    private String email; 
 
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
 
    public String getContactInformation() { 
        return contactInformation; 
    } 
 
    public void setContactInformation(String contactInformation) { 
        this.contactInformation = contactInformation; 
    } 
 
    public String getEmail() { 
        return email; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    } 
}