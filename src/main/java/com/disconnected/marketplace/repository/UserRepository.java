package com.disconnected.marketplace.repository; 
 
import com.disconnected.marketplace.entity.User; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
@Repository 
public interface UserRepository extends JpaRepository<User, Long> { 
} 