package com.oleksandrsyddia.financialanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);
            // Використання try-with-resources для автоматичного закриття потоку [cite: 48]
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;

                // Пропускаємо перший рядок (заголовок "Дата,Сума,Категорія")
                // Це виправлення, оскільки код у презентації [cite: 52]
                // не обробляє заголовок і впаде з NumberFormatException.
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 3) {
                        LocalDate date = LocalDate.parse(values[0], DATE_FORMATTER);
                        double amount = Double.parseDouble(values[1]);
                        String description = values[2];
                        transactions.add(new Transaction(date, amount, description));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка під час читання файлу: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }
}