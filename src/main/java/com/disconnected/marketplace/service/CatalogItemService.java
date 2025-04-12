package com.disconnected.marketplace.service; 
 
import com.disconnected.marketplace.entity.CatalogItem; 
import com.disconnected.marketplace.repository.CatalogItemRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import javax.validation.Valid; 
import java.util.List; 
import java.util.Optional; 
 
@Service 
public class CatalogItemService { 
 
    private static final Logger logger = LoggerFactory.getLogger(CatalogItemService.class); 
 
    @Autowired 
    private CatalogItemRepository catalogItemRepository; 
 
    public List<CatalogItem> findAll() { 
        logger.debug("Fetching all catalog items from the database."); 
        return catalogItemRepository.findAll(); 
    } 
 
    public Optional<CatalogItem> findById(Long id) { 
        logger.debug("Fetching catalog item with ID: {}", id); 
        return catalogItemRepository.findById(id); 
    } 
 
    public CatalogItem save(@Valid CatalogItem catalogItem) { 
        try { 
            logger.debug("Saving catalog item: {}", catalogItem); 
            return catalogItemRepository.save(catalogItem); 
        } catch (Exception e) { 
            logger.error("Error saving catalog item: ", e); 
            throw e; 
        } 
    } 
 
    public void deleteById(Long id) { 
        try { 
            logger.debug("Deleting catalog item with ID: {}", id); 
            catalogItemRepository.deleteById(id); 
        } catch (Exception e) { 
            logger.error("Error deleting catalog item with ID: {}", id, e); 
            throw e; 
        } 
    } 
}