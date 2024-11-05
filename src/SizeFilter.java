
import java.util.List;
import java.util.stream.Collectors;

public class SizeFilter implements Filter {
    
    private String size;

    public SizeFilter(String size) {
        this.size = size;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        return products.stream()
                .filter(product -> product.getSize().equalsIgnoreCase(size))
                .collect(Collectors.toList());
    }
}
