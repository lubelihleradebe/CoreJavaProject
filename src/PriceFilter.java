
import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter implements Filter{

    private double price;

    public PriceFilter(double price){
        this.price = price;
    
    }

    @Override
    public List<Product> apply(List<Product> products){
        return products.stream()
                .filter(product -> product.getPrice() == price)
                .collect(Collectors.toList());      
    }
}
