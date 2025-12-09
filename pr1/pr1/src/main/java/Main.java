package duikt.java.com;

import duikt.java.com.entities.Cart;
import duikt.java.com.entities.Category;
import duikt.java.com.entities.Order;
import duikt.java.com.entities.Product;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Electronics");
        Category smartphones = new Category(2, "Smartphones");
        Category accessories = new Category(3, "Accessories");

        Product product1 = new Product(
                1,
                "Laptop",
                19999.99,
                "High-performance laptop for work and gaming",
                electronics
        );

        Product product2 = new Product(
                2,
                "Smartphone",
                12999.50,
                "Smartphone with a large display",
                smartphones
        );

        Product product3 = new Product(
                3,
                "Headphones",
                2499.00,
                "Wireless noise-cancelling headphones",
                accessories
        );

        List<Product> catalog = Arrays.asList(product1, product2, product3);
        Cart cart = new Cart();

        List<Order> orderHistory = new ArrayList<>();
        Path historyFile = Path.of("order_history.txt");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1 - View product list");
            System.out.println("2 - Add product to cart");
            System.out.println("3 - View cart");
            System.out.println("4 - Place an order");
            System.out.println("5 - Remove product from cart");
            System.out.println("6 - View order history");
            System.out.println("7 - Search products (by name or category)");
            System.out.println("0 - Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(product1);
                    System.out.println(product2);
                    System.out.println(product3);
                    break;

                case 2:
                    System.out.println("Enter the product ID to add to cart:");
                    int id = scanner.nextInt();
                    if (id == 1) cart.addProduct(product1);
                    else if (id == 2) cart.addProduct(product2);
                    else if (id == 3) cart.addProduct(product3);
                    else System.out.println("Product with this ID was not found.");
                    break;

                case 3:
                    System.out.println(cart);
                    break;

                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Cart is empty. Add products before placing an order.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("Order has been placed:");
                        System.out.println(order);

                        try {
                            Files.writeString(
                                    historyFile,
                                    order.toString()
                                            + System.lineSeparator()
                                            + "------------------------"
                                            + System.lineSeparator(),
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.APPEND
                            );
                        } catch (Exception e) {
                            System.out.println("Failed to save order history to file: " + e.getMessage());
                        }
                        cart.clear();
                    }
                    break;

                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Cart is empty.");
                    } else {
                        System.out.println(cart);
                        System.out.println("Enter the product ID to remove from cart:");
                        int removeId = scanner.nextInt();
                        boolean removed = cart.removeProductById(removeId);
                        if (removed) System.out.println("Product removed from cart.");
                        else System.out.println("No product with this ID in the cart.");
                    }
                    break;

                case 6:
                    if (orderHistory.isEmpty()) {
                        System.out.println("Order history is empty.");
                    } else {
                        System.out.println("Order history:");
                        for (Order o : orderHistory) {
                            System.out.println(o);
                            System.out.println("------------------------");
                        }
                    }
                    break;

                case 7:
                    System.out.println("Choose search type:");
                    System.out.println("1 - By name");
                    System.out.println("2 - By category");
                    int searchType = scanner.nextInt();
                    scanner.nextLine();

                    if (searchType == 1) {
                        System.out.println("Enter part of the product name:");
                        String q = scanner.nextLine().toLowerCase();
                        List<Product> found = new ArrayList<>();
                        for (Product p : catalog) {
                            if (p.getName().toLowerCase().contains(q)) {
                                found.add(p);
                            }
                        }

                        if (found.isEmpty()) {
                            System.out.println("No products found.");
                        } else {
                            System.out.println("Found products:");
                            for (Product p : found) System.out.println(p);
                        }

                    } else if (searchType == 2) {
                        LinkedHashMap<Integer, String> categories = new LinkedHashMap<>();
                        for (Product p : catalog) {
                            if (p.getCategory() != null) {
                                categories.put(
                                        p.getCategory().getId(),
                                        p.getCategory().getName()
                                );
                            }
                        }

                        if (categories.isEmpty()) {
                            System.out.println("No categories available.");
                            break;
                        }

                        System.out.println("Available categories:");
                        for (var e : categories.entrySet()) {
                            System.out.println(e.getKey() + " - " + e.getValue());
                        }

                        System.out.println("Enter category ID or name:");
                        String input = scanner.nextLine().trim();

                        Integer categoryId = null;
                        try {
                            categoryId = Integer.parseInt(input);
                        } catch (NumberFormatException ignore) {}

                        List<Product> found = new ArrayList<>();
                        for (Product p : catalog) {
                            if (p.getCategory() == null) continue;

                            boolean byId =
                                    categoryId != null
                                            && p.getCategory().getId() == categoryId;

                            boolean byName =
                                    p.getCategory().getName() != null
                                            && p.getCategory().getName().equalsIgnoreCase(input);

                            if (byId || byName) found.add(p);
                        }

                        if (found.isEmpty()) {
                            System.out.println("No products found in this category.");
                        } else {
                            System.out.println("Found products:");
                            for (Product p : found) System.out.println(p);
                        }
                    } else {
                        System.out.println("Unknown search type.");
                    }
                    break;

                case 0:
                    System.out.println("Thank you for using our store!");
                    return;

                default:
                    System.out.println("Unknown option. Please try again.");
                    break;
            }
        }
    }
}