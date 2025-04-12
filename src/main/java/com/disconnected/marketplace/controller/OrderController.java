package com.disconnected.marketplace.controller; 
 
import com.disconnected.marketplace.entity.Order; 
import com.disconnected.marketplace.service.OrderService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.*; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice; 
import org.springframework.http.HttpStatus; 
import org.springframework.validation.FieldError; 
 
import java.util.List; 
import java.util.Optional; 
import java.util.HashMap; 
import java.util.Map; 
 
@RestController 
@RequestMapping("/api/orders") 
@Validated 
public class OrderController { 
 
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class); 
 
    @Autowired 
    private OrderService orderService; 
 
    @GetMapping 
    public List<Order> getAllOrders() { 
        logger.debug("Request to get all orders"); 
        return orderService.findAll(); 
    } 
 
    @GetMapping("/{id}") 
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) { 
        logger.debug("Request to get order by ID: {}", id); 
        Optional<Order> order = orderService.findById(id); 
        if (order.isPresent()) { 
            logger.debug("Order found: {}", order.get()); 
            return ResponseEntity.ok(order.get()); 
        } else { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
    @PostMapping 
    public ResponseEntity<Order> createOrder(@RequestBody @Validated Order order) { 
        try { 
            logger.debug("Request to create order: {}", order); 
            Order createdOrder = orderService.save(order); 
            logger.debug("Order created successfully: {}", createdOrder); 
            return ResponseEntity.ok(createdOrder); 
        } catch (Exception e) { 
            logger.error("Error creating order: ", e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}") 
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody @Validated Order order) { 
        logger.debug("Request to update order with ID: {}", id); 
        if (!orderService.findById(id).isPresent()) { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            order.setId(id); 
            Order updatedOrder = orderService.save(order); 
            logger.debug("Order updated successfully: {}", updatedOrder); 
            return ResponseEntity.ok(updatedOrder); 
        } catch (Exception e) { 
            logger.error("Error updating order with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) { 
        logger.debug("Request to delete order with ID: {}", id); 
        if (!orderService.findById(id).isPresent()) { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            orderService.deleteById(id); 
            logger.debug("Order deleted successfully with ID: {}", id); 
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) { 
            logger.error("Error deleting order with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}/accept") 
    public ResponseEntity<Order> acceptOrder(@PathVariable Long id) { 
        logger.debug("Request to accept order with ID: {}", id); 
        try { 
            Order updatedOrder = orderService.updateDeliveryStatus(id, "Accepted"); 
            return ResponseEntity.ok(updatedOrder); 
        } catch (IllegalArgumentException e) { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } catch (Exception e) { 
            logger.error("Error accepting order with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}/decline") 
    public ResponseEntity<Order> declineOrder(@PathVariable Long id) { 
        logger.debug("Request to decline order with ID: {}", id); 
        try { 
            Order updatedOrder = orderService.updateDeliveryStatus(id, "Declined"); 
            return ResponseEntity.ok(updatedOrder); 
        } catch (IllegalArgumentException e) { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } catch (Exception e) { 
            logger.error("Error declining order with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}/update-delivery-status") 
    public ResponseEntity<Order> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status) { 
        logger.debug("Request to update delivery status for order with ID: {}", id); 
        try { 
            Order updatedOrder = orderService.updateDeliveryStatus(id, status); 
            return ResponseEntity.ok(updatedOrder); 
        } catch (IllegalArgumentException e) { 
            logger.warn("Order not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } catch (Exception e) { 
            logger.error("Error updating delivery status for order with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
} 
 
@RestControllerAdvice 
class GlobalExceptionHandler { 
 
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); 
 
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) { 
        Map<String, String> errors = new HashMap<>(); 
        ex.getBindingResult().getAllErrors().forEach((error) -> { 
            String fieldName = ((FieldError) error).getField(); 
            String errorMessage = error.getDefaultMessage(); 
            errors.put(fieldName, errorMessage); 
        }); 
        logger.error("Validation error: {}", errors); 
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); 
    } 
 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleAllExceptions(Exception ex) { 
        logger.error("An error occurred: ", ex); 
        return new ResponseEntity<>("An internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
}