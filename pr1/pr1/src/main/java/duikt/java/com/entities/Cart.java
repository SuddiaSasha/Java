package duikt.java.com.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {

    @Getter
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public boolean removeProductById(int id) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public double getTotalPrice() {
        double sum = 0.0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Cart is empty.";
        }

        StringBuilder sb = new StringBuilder("Cart contains:\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("Total price: ").append(getTotalPrice());
        return sb.toString();
    }

    public void clear() {
        products.clear();
    }
}