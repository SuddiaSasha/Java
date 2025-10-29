package com.oleksandrsyddia.financialanalyzer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            String transactionMonthYear = transaction.getDate().format(MONTH_YEAR_FORMATTER);
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // ... (тут ваш попередній код: calculateTotalBalance, countTransactionsByMonth, findTopExpenses) ...

    /**
     * Самостійна робота: Визначення найбільших і найменших витрат за період.
     */
    public static List<Transaction> findMinMaxExpensesInPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        List<Transaction> expensesInPeriod = transactions.stream()
                .filter(t -> t.getAmount() < 0) // Тільки витрати
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .toList();

        if (expensesInPeriod.isEmpty()) {
            return Arrays.asList(null, null);
        }

        Transaction maxExpense = expensesInPeriod.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);

        Transaction minExpense = expensesInPeriod.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);

        return Arrays.asList(minExpense, maxExpense);
    }

    public static java.util.Map<String, Double> calculateTotalExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(java.util.stream.Collectors.groupingBy(
                        Transaction::getDescription,
                        java.util.stream.Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public static java.util.Map<String, Double> calculateTotalExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(java.util.stream.Collectors.groupingBy(
                        t -> t.getDate().format(MONTH_YEAR_FORMATTER),
                        java.util.stream.Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}