package com.shop.processor;

import com.shop.model.Product;

public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public void processOrder() {
        try {
            String message = String.format("Processing: %s | Price: %.2f | Thread: %s",
                    product.getName(),
                    product.getPrice(),
                    Thread.currentThread().getName());
            System.out.println(message);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}