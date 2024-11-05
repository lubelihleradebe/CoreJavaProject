
import java.util.List;
import java.util.stream.Collectors;

public class TypeFilter implements Filter{

    private String type;

    public TypeFilter(String type){
        this.type = type;
    }

    @Override
    public List<Product> apply(List<Product> products){
        return products.stream()
            .filter(product -> product.getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());
    }



}
