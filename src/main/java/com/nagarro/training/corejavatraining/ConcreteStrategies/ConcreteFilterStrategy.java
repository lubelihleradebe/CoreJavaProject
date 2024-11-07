package com.nagarro.training.corejavatraining.ConcreteStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.Product;
import com.nagarro.training.corejavatraining.interfaces.FilterStrategy;

public class ConcreteFilterStrategy implements FilterStrategy{

    
    @Override
    public List<Product> filter(Map<String, String> criteria, ConcurrentMap<String, Product> productMap) {
        // List of filtered products
        List<Product> filteredProducts = new ArrayList<>();

        // Iterate over all the products
        for(Product product: productMap.values()){
            boolean isMatch = true;

            // Check if the product matches the criteria
            for(Map.Entry<String, String> entry: criteria.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();

                // Check if the product has the required attribute
                switch(key){
                    case "color":
                        if(!product.getColor().equalsIgnoreCase(value)){
                            isMatch = false;
                        }
                        break;
                    case "size":
                        if(!product.getSize().equalsIgnoreCase(value)){
                            isMatch = false;
                        }
                        break;
                    case "brand":
                        if(!product.getBrand().equalsIgnoreCase(value)){
                            isMatch = false;
                        }
                        break;
                    default:
                        System.out.println("Invalid criteria.");
                        break;
                }
            }

            // If the product matches the criteria, add it to the list
            if(isMatch){
                filteredProducts.add(product);
                
            }else if(!isMatch) {
                System.out.println("No products found.");
                break;
            }
        }
        System.out.println("Product Map /size: " + productMap.size());

        return filteredProducts;
    }
    

}
