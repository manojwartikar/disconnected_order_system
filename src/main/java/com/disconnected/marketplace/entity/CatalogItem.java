package com.disconnected.marketplace.entity; 
 
import javax.persistence.*; 
import javax.validation.constraints.NotBlank; 
import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Positive; 
import javax.validation.constraints.Size; 
import java.math.BigDecimal; 
 
@Entity 
public class CatalogItem { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    @NotBlank(message = "Description is mandatory") 
    @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters") 
    private String description; 
 
    @NotNull(message = "Price is mandatory") 
    @Positive(message = "Price must be positive") 
    private BigDecimal price; 
 
    // Getters and Setters 
 
    public Long getId() { 
        return id; 
    } 
 
    public void setId(Long id) { 
        this.id = id; 
    } 
 
    public String getDescription() { 
        return description; 
    } 
 
    public void setDescription(String description) { 
        this.description = description; 
    } 
 
    public BigDecimal getPrice() { 
        return price; 
    } 
 
    public void setPrice(BigDecimal price) { 
        this.price = price; 
    } 
}