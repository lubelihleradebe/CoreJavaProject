
import java.util.ArrayList;
import java.util.List;

public class FilterManager {

    private final List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter){
        System.out.println("something being filtered");
        filters.add(filter);
    }

    public List<Product> applyFilters(List<Product> products){
        CompositeFilter combinedFilters = new CompositeFilter(filters);
        System.out.println("products here in filter manager"+products);
        return combinedFilters.apply(products);
    }

    public void clearFilters(){
        filters.clear();
    }

}
