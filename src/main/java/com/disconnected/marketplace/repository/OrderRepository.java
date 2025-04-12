package com.disconnected.marketplace.repository; 
 
import com.disconnected.marketplace.entity.Order; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
@Repository 
public interface OrderRepository extends JpaRepository<Order, Long> { 
} 