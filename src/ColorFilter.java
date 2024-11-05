

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;

public class ColorFilter implements Filter {
    private final String color;

    public ColorFilter(String color) {
        this.color = color.toLowerCase(); // Normalize to lowercase
    }
  

    @Override
    public List<Product> apply(List<Product> products) {
        System.out.println("Applying color filter: " + color);
        List<Product> filteredProducts = new ArrayList<>();
        System.out.println(products + "products here");
        for (Product product : products) {
            System.out.println("production here"+product);
            System.out.println("Product color: " + product.getColor());
            // Debugging statements
            System.out.println("Comparing product color: " + product.getColor() + " with filter color: " + color);

            if (product.getColor().equalsIgnoreCase(color)) { // Ignore case during comparison
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}


// public class ColorFilter implements Filter {
//     private String color;

//     public ColorFilter(String color) {
//         this.color = color;
//     }

//     @Override
//     public List<Product> apply(List<Product> products) {
//         return products.stream() // Convert list to stream
//                 .filter(product -> product.getColor().equalsIgnoreCase(color)) // Filter by color using lambda
//                 .collect(Collectors.toList()); // Collect results to a new list
//     }
// }
