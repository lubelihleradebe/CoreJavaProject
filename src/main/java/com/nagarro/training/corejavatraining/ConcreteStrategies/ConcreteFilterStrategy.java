package com.nagarro.training.corejavatraining.ConcreteStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.Interfaces.FilterStrategy;
import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

public class ConcreteFilterStrategy implements FilterStrategy {

    private final List<FilterCriterion> filterCriteria;

    // Constructor to accept a list of filter criteria
    public ConcreteFilterStrategy(List<FilterCriterion> filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    @Override
    public List<Product> filter(Map<String, String> criteria, ConcurrentMap<CompositeKey, Product> productMap) {
        List<Product> filteredProducts = new ArrayList<>();
        System.out.println("its here "+productMap.size());

        for (Product product : productMap.values()) {
            boolean isMatch = true;
        
            // Debugging output to show each product check
            System.out.println("Checking product: " + product.getBrand());
        
            // Loop through each criterion and apply them
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println("Checking for " + key + "=" + value);
        
                // Find the matching filter criterion
                FilterCriterion filterCriterion = getFilterCriterionForKey(key);
                if (filterCriterion != null) {
                    boolean match = filterCriterion.matches(product, value);
                    System.out.println("Match for " + key + ": " + match);
                    if (!match) {
                        isMatch = false;
                        break;
                    }
                }
            }
        
            // If the product passes all criteria, add it to the filtered list
            if (isMatch) {
                filteredProducts.add(product);
            }
        }
        

        if (filteredProducts.isEmpty()) {
            System.out.println("No products found matching the criteria.");
        }

        return filteredProducts;
    }

    private FilterCriterion getFilterCriterionForKey(String key) {
        for (FilterCriterion criterion : filterCriteria) {
            if (criterion.getClass().getSimpleName().toLowerCase().contains(key.toLowerCase())) {
                return criterion;
            }
        }
        return null; // No matching criterion found for the key
    }
}
