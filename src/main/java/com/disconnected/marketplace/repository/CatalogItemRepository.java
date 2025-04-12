package com.disconnected.marketplace.repository; 
 
import com.disconnected.marketplace.entity.CatalogItem; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
@Repository 
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> { 
} 