package com.disconnected.marketplace.service; 
 
import com.disconnected.marketplace.entity.Supplier; 
import com.disconnected.marketplace.repository.SupplierRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import javax.validation.Valid; 
import java.util.List; 
import java.util.Optional; 
 
@Service 
public class SupplierService { 
 
    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class); 
 
    @Autowired 
    private SupplierRepository supplierRepository; 
 
    public List<Supplier> findAll() { 
        logger.debug("Fetching all suppliers from the database."); 
        return supplierRepository.findAll(); 
    } 
 
    public Optional<Supplier> findById(Long id) { 
        logger.debug("Fetching supplier with ID: {}", id); 
        return supplierRepository.findById(id); 
    } 
 
    public Supplier save(@Valid Supplier supplier) { 
        try { 
            logger.debug("Saving supplier: {}", supplier); 
            return supplierRepository.save(supplier); 
        } catch (Exception e) { 
            logger.error("Error saving supplier: ", e); 
            throw e; 
        } 
    } 
 
    public void deleteById(Long id) { 
        try { 
            logger.debug("Deleting supplier with ID: {}", id); 
            supplierRepository.deleteById(id); 
        } catch (Exception e) { 
            logger.error("Error deleting supplier with ID: {}", id, e); 
            throw e; 
        } 
    } 
 
    // Additional business logic can be added here 
} 