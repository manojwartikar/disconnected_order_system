package com.disconnected.marketplace.service; 
 
import org.springframework.amqp.rabbit.core.RabbitTemplate; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
@Service 
public class NotificationService { 
 
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class); 
 
    @Autowired 
    private RabbitTemplate rabbitTemplate; 
 
    public void sendOrderStatusChangeNotification(Long orderId, String status) { 
        try { 
            String message = "Order ID: " + orderId + " status changed to: " + status; 
            rabbitTemplate.convertAndSend("orderStatusExchange", "order.status", message); 
            logger.info("Notification sent for order ID: {}", orderId); 
        } catch (Exception e) { 
            logger.error("Error sending notification for order ID: {}", orderId, e); 
        } 
    } 
} 