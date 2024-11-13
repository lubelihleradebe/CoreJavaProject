import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.nagarro.training.corejavatraining.ConcreteStrategies.ColorFilterCriterion;
import com.nagarro.training.corejavatraining.ConcreteStrategies.ConcreteFilterStrategy;
import com.nagarro.training.corejavatraining.ConcreteStrategies.SizeFilterCriterion;
import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

public class FilterLogicTest {

    
    @Test
    void testFilter_withMixedCaseCriteria() {
        // Mock data setup
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product = new Product("1", "Nike", "Red", "M", "Shoes");
        productMap.put(new CompositeKey("1", "Nike"), product);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        ConcreteFilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "rEd"); // Mixed case for color
        criteria.put("size", "m");    // Lowercase for size

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertEquals(1, filteredProducts.size());
        assertEquals("Nike", filteredProducts.get(0).getBrand());
    }

    @Test
    void testFilter_withPartialMatch() {
        // Mock data setup
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product = new Product("1", "Nike", "Red", "M", "Shoes");
        productMap.put(new CompositeKey("1", "Nike"), product);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        ConcreteFilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "R"); // Partial match for color
        criteria.put("size", "M");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertEquals(0, filteredProducts.size()); // Expect no match
    }


    @Test
    void testFilter_withNonExistentAttributeValue() {
        // Mock data setup
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product = new Product("1", "Puma", "Gold", "M", "Shirt");
        productMap.put(new CompositeKey("1", "Puma"), product);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        ConcreteFilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "Green"); // Non-existent color
        criteria.put("size", "M");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertEquals(0, filteredProducts.size()); // Expect no match
    }

    @Test
    void testFilter_withMultipleNonMatchingCriteria() {
        // Mock data setup
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();
        Product product1 = new Product("1", "Puma", "Blue", "M", "Shoes");
        Product product2 = new Product("2", "Adidas", "Grey", "L", "Shirt");
        productMap.put(new CompositeKey("1", "Nike"), product1);
        productMap.put(new CompositeKey("2", "Adidas"), product2);

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        ConcreteFilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "Red");
        criteria.put("size", "L");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertEquals(0, filteredProducts.size()); // Expect no match
    }


    @Test
    void testFilter_withEmptyProductMap() {
        // Empty product map
        ConcurrentMap<CompositeKey, Product> productMap = new ConcurrentHashMap<>();

        List<FilterCriterion> criteriaList = Arrays.asList(new ColorFilterCriterion(), new SizeFilterCriterion());
        ConcreteFilterStrategy filterStrategy = new ConcreteFilterStrategy(criteriaList);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("color", "Red");
        criteria.put("size", "M");

        List<Product> filteredProducts = filterStrategy.filter(criteria, productMap);

        assertEquals(0, filteredProducts.size()); // Expect no match, no errors
    }


}
