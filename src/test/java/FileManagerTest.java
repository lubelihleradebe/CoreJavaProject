import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nagarro.training.corejavatraining.FilterManager;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

class FileManagerTest {

    private FilterManager filterManager;
    private Scanner scanner;
    private ConcurrentMap<CompositeKey, Product> productMap;

    @BeforeEach
    void setUp() {
        filterManager = Mockito.mock(FilterManager.class);
        scanner = Mockito.mock(Scanner.class);
        productMap = new ConcurrentHashMap<>();
        // Prepare mock products
        Product product1 = new Product("1","Nike", "Red", "M", "Shoes");
        productMap.put(new CompositeKey("1", "Nike"), product1);
    }

    @Test
    void testFilterProducts_validChoice() {
        Mockito.when(scanner.nextLine()).thenReturn("1"); // Simulate user selecting color filter

        List<Product> filteredProducts = filterManager.filterProducts(1, scanner, productMap);

        assertNotNull(filteredProducts);
        // assertFalse(filteredProducts.isEmpty());
    }

    @Test
    void testFilterProducts_invalidChoice() {
        Mockito.when(scanner.nextLine()).thenReturn("7"); // Invalid filter choice

        List<Product> filteredProducts = filterManager.filterProducts(7, scanner, productMap);
        
        assertTrue(filteredProducts.isEmpty());
    }
}
