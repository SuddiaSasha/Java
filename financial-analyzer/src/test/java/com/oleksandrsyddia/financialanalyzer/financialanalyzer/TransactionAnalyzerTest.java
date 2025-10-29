package com.oleksandrsyddia.financialanalyzer.financialanalyzer;

import com.oleksandrsyddia.financialanalyzer.Transaction;
import com.oleksandrsyddia.financialanalyzer.TransactionAnalyzer;
import com.oleksandrsyddia.financialanalyzer.TransactionCSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Transaction t1 = new Transaction(LocalDate.parse("01-01-2023", FORMATTER), 100.0, "Дохід");
    private Transaction t2 = new Transaction(LocalDate.parse("02-01-2023", FORMATTER), -50.0, "Витрата 1");
    private Transaction t3 = new Transaction(LocalDate.parse("03-01-2023", FORMATTER), 150.0, "Дохід");
    private Transaction t4 = new Transaction(LocalDate.parse("01-02-2023", FORMATTER), -200.0, "Витрата 2");
    private Transaction t5 = new Transaction(LocalDate.parse("05-02-2023", FORMATTER), -300.0, "Витрата 3");

    @BeforeEach
    public void setUp() {
        testTransactions = Arrays.asList(t1, t2, t3, t4, t5);
    }


    @Test
    public void testCalculateTotalBalance() {
        double result = TransactionAnalyzer.calculateTotalBalance(testTransactions);
        Assertions.assertEquals(-300.0, result, "Розрахунок загального балансу неправильний");
    }


    @Test
    public void testCountTransactionsByMonth() {
        int countJan = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "01-2023");
        int countFeb = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "03-2023");

        Assertions.assertEquals(3, countJan, "Кількість транзакцій за січень неправильна");
        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(0, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(testTransactions);

        Assertions.assertEquals(3, topExpenses.size(), "Кількість витрат неправильна");
        Assertions.assertEquals(-300.0, topExpenses.get(0).getAmount(), "Перша найбільша витрата неправильна");
        Assertions.assertEquals(-200.0, topExpenses.get(1).getAmount(), "Друга найбільша витрата неправильна");
        Assertions.assertEquals(-50.0, topExpenses.get(2).getAmount(), "Третя найбільша витрата неправильна");
    }


    @Test
    public void testReadTransactionsFromCSV() {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій не повинен бути порожнім");
        Transaction firstTransaction = transactions.get(0);
        Assertions.assertEquals(LocalDate.parse("05-12-2023", FORMATTER), firstTransaction.getDate());
        Assertions.assertEquals(-450.0, firstTransaction.getAmount());
        Assertions.assertEquals("Сільпо", firstTransaction.getDescription());
    }
}