package com.disconnected.marketplace.controller; 
 
import com.disconnected.marketplace.entity.CatalogItem; 
import com.disconnected.marketplace.service.CatalogItemService; 
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
@RequestMapping("/api/catalog-items") 
@Validated 
public class CatalogItemController { 
 
    private static final Logger logger = LoggerFactory.getLogger(CatalogItemController.class); 
 
    @Autowired 
    private CatalogItemService catalogItemService; 
 
    @GetMapping 
    public List<CatalogItem> getAllCatalogItems() { 
        logger.debug("Request to get all catalog items"); 
        return catalogItemService.findAll(); 
    } 
 
    @GetMapping("/{id}") 
    public ResponseEntity<CatalogItem> getCatalogItemById(@PathVariable Long id) { 
        logger.debug("Request to get catalog item by ID: {}", id); 
        Optional<CatalogItem> catalogItem = catalogItemService.findById(id); 
        if (catalogItem.isPresent()) { 
            logger.debug("Catalog item found: {}", catalogItem.get()); 
            return ResponseEntity.ok(catalogItem.get()); 
        } else { 
            logger.warn("Catalog item not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
    @PostMapping 
    public ResponseEntity<CatalogItem> createCatalogItem(@RequestBody @Validated CatalogItem catalogItem) { 
        try { 
            logger.debug("Request to create catalog item: {}", catalogItem); 
            CatalogItem createdCatalogItem = catalogItemService.save(catalogItem); 
            logger.debug("Catalog item created successfully: {}", createdCatalogItem); 
            return ResponseEntity.ok(createdCatalogItem); 
        } catch (Exception e) { 
            logger.error("Error creating catalog item: ", e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @PutMapping("/{id}") 
    public ResponseEntity<CatalogItem> updateCatalogItem(@PathVariable Long id, @RequestBody @Validated CatalogItem catalogItem) { 
        logger.debug("Request to update catalog item with ID: {}", id); 
        if (!catalogItemService.findById(id).isPresent()) { 
            logger.warn("Catalog item not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            catalogItem.setId(id); 
            CatalogItem updatedCatalogItem = catalogItemService.save(catalogItem); 
            logger.debug("Catalog item updated successfully: {}", updatedCatalogItem); 
            return ResponseEntity.ok(updatedCatalogItem); 
        } catch (Exception e) { 
            logger.error("Error updating catalog item with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        } 
    } 
 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteCatalogItem(@PathVariable Long id) { 
        logger.debug("Request to delete catalog item with ID: {}", id); 
        if (!catalogItemService.findById(id).isPresent()) { 
            logger.warn("Catalog item not found with ID: {}", id); 
            return ResponseEntity.notFound().build(); 
        } 
        try { 
            catalogItemService.deleteById(id); 
            logger.debug("Catalog item deleted successfully with ID: {}", id); 
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) { 
            logger.error("Error deleting catalog item with ID: {}", id, e); 
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