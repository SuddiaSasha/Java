package com.oleksandrsyddia.financialanalyzer;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        TransactionReportGenerator.printBalanceReport(totalBalance);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);


        // --- Додано для самостійної роботи ---

        // 1. Звіт про мін/макс витрати за період
        LocalDate start = LocalDate.of(2023, 12, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);
        List<Transaction> minMax = TransactionAnalyzer.findMinMaxExpensesInPeriod(transactions, start, end);
        TransactionReportGenerator.printMinMaxExpensesReport(minMax);

        // 2. Звіт по категоріях
        java.util.Map<String, Double> expensesByCategory = TransactionAnalyzer.calculateTotalExpensesByCategory(transactions);
        TransactionReportGenerator.printExpensesReport(expensesByCategory, "Звіт по категоріях");

        // 3. Звіт по місяцях
        java.util.Map<String, Double> expensesByMonth = TransactionAnalyzer.calculateTotalExpensesByMonth(transactions);
        TransactionReportGenerator.printExpensesReport(expensesByMonth, "Звіт по місяцх");

    }
}