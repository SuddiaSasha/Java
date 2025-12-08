package com.shop.main;

import com.github.javafaker.Faker;
import com.shop.model.Clothing;
import com.shop.model.Electronics;
import com.shop.model.Product;
import com.shop.processor.OrderProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        List<OrderProcessor<? extends Product>> orders = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Electronics electronics = Electronics.builder()
                    .id(faker.idNumber().valid())
                    .name(faker.commerce().productName())
                    .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                    .warrantyPeriod("24 months")
                    .build();
            orders.add(new OrderProcessor<>(electronics));
        }

        for (int i = 0; i < 5; i++) {
            Clothing clothing = Clothing.builder()
                    .id(faker.idNumber().valid())
                    .name(faker.commerce().productName())
                    .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                    .size("L")
                    .material(faker.commerce().material())
                    .build();
            orders.add(new OrderProcessor<>(clothing));
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (OrderProcessor<? extends Product> order : orders) {
            executor.submit(() -> order.processOrder());
        }

        executor.shutdown();
    }
}