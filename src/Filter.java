

import java.util.List;

public interface Filter {
    List<Product> apply(List<Product> products);
}
