import java.util.List;

public class CompositeFilter implements Filter {
    private final List<Filter> filters;

    public CompositeFilter(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        System.out.println(products + "products here in composite filter");
        List<Product> result = products;
        for (Filter filter : filters) {
            result = filter.apply(result); // Apply each filter in sequence
        }
        return result;
    }
}
