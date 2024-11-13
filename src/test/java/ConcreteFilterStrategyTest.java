import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.nagarro.training.corejavatraining.ConcreteStrategies.ColorFilterCriterion;
import com.nagarro.training.corejavatraining.ConcreteStrategies.ConcreteFilterStrategy;
import com.nagarro.training.corejavatraining.ConcreteStrategies.SizeFilterCriterion;
import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.Interfaces.FilterStrategy;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

class ConcreteFilterStrategyTest {

    @Test
    public void testFilter_withValidCriteria() {
        // Prepare mock data
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        
        // Create test products
        Product product1 = new Product("1", "Nike","Red", "M", "Shoes");
        Product product2 = new Product("2", "Adidas", "Blue", "L" , "Shirt");
        
        // Add products to the map using CompositeKey
        productMap.put(new CompositeKey("1", "Nike"), product1);
        productMap.put(new CompositeKey("2", "Adidas"), product2);

        System.out.println("the imds size is: "+productMap.size());

        

        // Define filtering criteria
        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "red");
        criteria.put("size", "m");


        System.out.println(criteria);

        // Define the filter strategy with color and size criteria
        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        FilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        // Call filter method
        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        System.out.println("And the products are: "+filteredProducts);

        // Assertions to validate filtering logic
        assertEquals(1, filteredProducts.size(), "Filtered products size should be 1");
        assertEquals("Nike", filteredProducts.get(0).getBrand(), "Filtered product should be from Nike");
    }


    @Test
    void testFilter_withNoMatchingCriteria() {
        // Prepare mock data
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product1 = new Product("1","Red", "M", "Nike", "Shoes");
        productMap.put(new CompositeKey("1","Nike"), product1);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        FilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "Blue");
        criteria.put("size", "L");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertTrue(filteredProducts.isEmpty());
    }

    @Test
    void testFilter_withInvalidCriteriaKey() {
        // Prepare mock data
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product1 = new Product("3","Nike", "Red", "L", "Shoes");
        productMap.put(new CompositeKey("3", "Nike"), product1);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion());
        FilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "SomeValue");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);
        System.out.println(filteredProducts);

        assertTrue(filteredProducts.isEmpty());
    }
}
