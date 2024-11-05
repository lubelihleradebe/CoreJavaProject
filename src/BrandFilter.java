

import java.util.List;
import java.util.stream.Collectors;

public class BrandFilter implements Filter{
    private String brand;

    public BrandFilter(String brand){
        this.brand = brand; 
    }

    @Override
    public List<Product> apply(List<Product> products){
        return products.stream()
            .filter(product -> product.getBrand().equalsIgnoreCase(brand))
            .collect(Collectors.toList());
        
    }



}
