package com.disconnected.marketplace.service; 
 
import com.disconnected.marketplace.entity.Order; 
import com.disconnected.marketplace.repository.OrderRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import javax.validation.Valid; 
import java.util.List; 
import java.util.Optional; 
 
@Service 
public class OrderService { 
 
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class); 
 
    @Autowired 
    private OrderRepository orderRepository; 
 
    @Autowired 
    private NotificationService notificationService; 
 
    public List<Order> findAll() { 
        logger.debug("Fetching all orders from the database."); 
        return orderRepository.findAll(); 
    } 
 
    public Optional<Order> findById(Long id) { 
        logger.debug("Fetching order with ID: {}", id); 
        return orderRepository.findById(id); 
    } 
 
    public Order save(@Valid Order order) { 
        try { 
            logger.debug("Saving order: {}", order); 
            Order savedOrder = orderRepository.save(order); 
            notificationService.sendOrderStatusChangeNotification(savedOrder.getId(), savedOrder.getStatus()); 
            return savedOrder; 
        } catch (Exception e) { 
            logger.error("Error saving order: ", e); 
            throw e; 
        } 
    } 
 
    public void deleteById(Long id) { 
        try { 
            logger.debug("Deleting order with ID: {}", id); 
            orderRepository.deleteById(id); 
        } catch (Exception e) { 
            logger.error("Error deleting order with ID: {}", id, e); 
            throw e; 
        } 
    } 
 
    public Order updateDeliveryStatus(Long orderId, String status) { 
        try { 
            logger.debug("Updating delivery status for order ID: {}", orderId); 
            Optional<Order> orderOptional = orderRepository.findById(orderId); 
            if (orderOptional.isPresent()) { 
                Order order = orderOptional.get(); 
                order.setDeliveryStatus(status); 
                Order updatedOrder = orderRepository.save(order); 
                notificationService.sendOrderStatusChangeNotification(updatedOrder.getId(), updatedOrder.getDeliveryStatus()); 
                return updatedOrder; 
            } else { 
                logger.warn("Order not found with ID: {}", orderId); 
                throw new IllegalArgumentException("Order not found"); 
            } 
        } catch (Exception e) { 
            logger.error("Error updating delivery status for order ID: {}", orderId, e); 
            throw e; 
        } 
    } 
}