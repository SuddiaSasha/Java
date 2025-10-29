package com.oleksandrsyddia.financialanalyzer;

import java.util.List;

public abstract class TransactionReportGenerator {


    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    /**
     * Самостійна робота: Виведення звіту про мін/макс витрати.
     */
    public static void printMinMaxExpensesReport(List<Transaction> minMax) {
        Transaction minExpense = minMax.get(0);
        Transaction maxExpense = minMax.get(1);

        System.out.println("\n--- Звіт за періодом (Мін/Макс витрати) ---");
        if (minExpense != null) {
            System.out.println("Найменша витрата: " + minExpense.getDescription() + " (" + minExpense.getAmount() + ")");
        } else {
            System.out.println("Найменша витрата: не знайдено");
        }
        if (maxExpense != null) {
            System.out.println("Найбільша витрата: " + maxExpense.getDescription() + " (" + maxExpense.getAmount() + ")");
        } else {
            System.out.println("Найбільша витрата: не знайдено");
        }
    }

    /**
     * Самостійна робота: Створення текстового звіту по категоріях або місяцях.
     */
    public static void printExpensesReport(java.util.Map<String, Double> expenseMap, String title) {
        // Масштаб візуалізації: 1 символ = 1000 грн
        final int SCALE = 1000;

        System.out.println("\n--- " + title + " ---");

        expenseMap.entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByValue())
                .forEach(entry -> {
                    String category = entry.getKey();
                    double amount = entry.getValue();
                    int starsCount = (int) (Math.abs(amount) / SCALE);
                    String visualization = "*".repeat(starsCount); //

                    System.out.printf("%-20s: %10.2f грн. | %s\n", category, amount, visualization);
                });
        System.out.println("(1 '*' приблизно дорівнює " + SCALE + " грн. витрат)");
    }

}