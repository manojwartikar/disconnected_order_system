package com.disconnected.marketplace.repository; 
 
import com.disconnected.marketplace.entity.Supplier; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
@Repository 
public interface SupplierRepository extends JpaRepository<Supplier, Long> { 
} 