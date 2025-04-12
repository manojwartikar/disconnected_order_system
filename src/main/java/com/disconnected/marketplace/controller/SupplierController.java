package com.disconnected.marketplace.controller; 
 
import com.disconnected.marketplace.entity.Supplier; 
import com.disconnected.marketplace.service.SupplierService; 
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
@RequestMapping("/api/suppliers") 
@Validated 
public class SupplierController { 
 
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class); 
 
    @Autowired 
    private SupplierService supplierService; 
 
    @GetMapping 
    public List<Supplier> getAllSuppliers() { 
        logger.debug("Request to get all suppliers"); 
        return supplierService.findAll(); 
    } 
 
    @GetMapping("/{id}") 
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) { 
        logger.debug("Request to get supplier by ID: {}", id); 
        Optional<Supplier> supplier = supplierService.findById(id); 
        if (supplier.isPresent()) { 
            logger.debug("Supplier found: {}", supplier.get()); 
            return ResponseEntity.ok(supplier.get()); 
        } else { 
            logger.warn("Supplier not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
    @PostMapping 
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Validated Supplier supplier) { 
        try { 
            logger.debug("Request to create supplier: {}", supplier); 
            Supplier createdSupplier = supplierService.save(supplier); 
            logger.debug("Supplier created successfully: {}", createdSupplier); 
            return ResponseEntity.ok(createdSupplier); 
        } catch (Exception e) { 
            logger.error("Error creating supplier: ", e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}") 
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody @Validated Supplier supplier) { 
        logger.debug("Request to update supplier with ID: {}", id); 
        if (!supplierService.findById(id).isPresent()) { 
            logger.warn("Supplier not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            supplier.setId(id); 
            Supplier updatedSupplier = supplierService.save(supplier); 
            logger.debug("Supplier updated successfully: {}", updatedSupplier); 
            return ResponseEntity.ok(updatedSupplier); 
        } catch (Exception e) { 
            logger.error("Error updating supplier with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) { 
        logger.debug("Request to delete supplier with ID: {}", id); 
        if (!supplierService.findById(id).isPresent()) { 
            logger.warn("Supplier not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            supplierService.deleteById(id); 
            logger.debug("Supplier deleted successfully with ID: {}", id); 
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) { 
            logger.error("Error deleting supplier with ID: {}", id, e); 
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