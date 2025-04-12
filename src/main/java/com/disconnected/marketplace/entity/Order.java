package com.disconnected.marketplace.entity; 
 
import javax.persistence.*; 
import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Positive; 
import javax.validation.constraints.Size; 
import java.math.BigDecimal; 
 
@Entity 
public class Order { 
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    @NotNull(message = "Product details are mandatory") 
    @Size(min = 1, max = 255, message = "Product details must be between 1 and 255 characters") 
    private String productDetails; 
 
    @NotNull(message = "Quantity is mandatory") 
    @Positive(message = "Quantity must be positive") 
    private Integer quantity; 
 
    @NotNull(message = "Price is mandatory") 
    @Positive(message = "Price must be positive") 
    private BigDecimal price; 
 
    @NotNull(message = "Status is mandatory") 
    private String status; 
 
    @NotNull(message = "Delivery status is mandatory") 
    private String deliveryStatus; 
 
    // Getters and Setters 
 
    public Long getId() { 
        return id; 
    } 
 
    public void setId(Long id) { 
        this.id = id; 
    } 
 
    public String getProductDetails() { 
        return productDetails; 
    } 
 
    public void setProductDetails(String productDetails) { 
        this.productDetails = productDetails; 
    } 
 
    public Integer getQuantity() { 
        return quantity; 
    } 
 
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
    } 
 
    public BigDecimal getPrice() { 
        return price; 
    } 
 
    public void setPrice(BigDecimal price) { 
        this.price = price; 
    } 
 
    public String getStatus() { 
        return status; 
    } 
 
    public void setStatus(String status) { 
        this.status = status; 
    } 
 
    public String getDeliveryStatus() { 
        return deliveryStatus; 
    } 
 
    public void setDeliveryStatus(String deliveryStatus) { 
        this.deliveryStatus = deliveryStatus; 
    } 
}