

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nagarro.training.corejavatraining.ConcreteStrategies.ConcreteFilterStrategy;
import com.nagarro.training.corejavatraining.Interfaces.FilterStrategy;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

class ConcreteFilterStrategyTest {

    private FilterStrategy filterStrategy;
    private ConcurrentMap<CompositeKey, Product> productMap;

    @BeforeEach
    void setUp() {
        filterStrategy = new ConcreteFilterStrategy();
        productMap = new ConcurrentHashMap<>();
        
        // Add sample products
        productMap.put(new CompositeKey("1", "Nike"), new Product("Nike", "Red", "M", 50.0, "Shoes"));
        productMap.put(new CompositeKey("2", "Nike"), new Product("Nike", "Blue", "L", 60.0, "Shoes"));
        productMap.put(new CompositeKey("3", "Puma"), new Product("Puma", "Red", "S", 40.0, "Shoes"));
    }

    @Test
    public void testFilterByColor() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "Red");
        
        List<Product> result = filterStrategy.filter(criteria, productMap);
        System.out.println("result size is"+result.size());
        assertEquals(2, result.size(), "There should be 2 products with color Red");
    }

    @Test
    void testFilterByBrandAndSize() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("brand", "Nike");
        criteria.put("size", "M");
        
        List<Product> result = filterStrategy.filter(criteria, productMap);
        assertEquals(1, result.size(), "There should be 1 product with brand Nike and size M");
    }
}
