package bill;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MobileBillingSystem {
    private static final double PLAN_A_CALL_RATE = 6.0;
    private static final double PLAN_A_SMS_RATE = 2.0;
    private static final double PLAN_A_INTERNET_RATE = 3.0;
    private static final double PLAN_B_FIXED_PRICE = 700.0;
    private static final int PLAN_B_CALL_MINUTES = 200;
    private static final int PLAN_B_SMS_COUNT = 200;
    private static final int PLAN_B_INTERNET_USAGE = 2000;
    private static final double PLAN_C_FIXED_PRICE = 800.0;
    private static final double PLAN_C_INTERNET_RATE = 1.5;
    private static final double PLAN_D_FIXED_PRICE = 1000.0;
    private static final double PLAN_D_CALL_RATE = 4.0;
    private static final double PLAN_D_SMS_RATE = 1.5;
    private static final double PLAN_E_FIXED_PRICE = 1500.0;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the input file as a command line argument.");
            return;
        }

        String inputFile = args[0];
        List<Customer> customers = readInputFile(inputFile);
        List<Bill> bills = calculateBills(customers);
        writeOutputFile(bills);
    }

    static List<Customer> readInputFile(String inputFile) {
        List<Customer> customers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(inputFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                String name = data[0];
                String plan = data[1];
                int callMinutes = Integer.parseInt(data[2]);
                int smsCount = Integer.parseInt(data[3]);
                int internetUsage = Integer.parseInt(data[4]);

                Customer customer = new Customer(name, plan, callMinutes, smsCount, internetUsage);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
        }

        return customers;
    }

    static List<Bill> calculateBills(List<Customer> customers) {
        List<Bill> bills = new ArrayList<>();

        for (Customer customer : customers) {
            double preTaxAmount = 0.0;

            switch (customer.getPlan()) {
                case "A":
                    preTaxAmount = calculatePlanA(customer);
                    break;
                case "B":
                    preTaxAmount = calculatePlanB(customer);
                    break;
                case "C":
                    preTaxAmount = calculatePlanC(customer);
                    break;
                case "D":
                    preTaxAmount = calculatePlanD(customer);
                    break;
                case "E":
                    preTaxAmount = PLAN_E_FIXED_PRICE;
                    break;
            }

            double tax = preTaxAmount < 2500 ? preTaxAmount * 0.15 : preTaxAmount * 0.20;
            double withTaxAmount = preTaxAmount + tax;

            Bill bill = new Bill(customer, preTaxAmount, tax, withTaxAmount);
            bills.add(bill);
        }

        // Sort bills in ascending order of with-tax amount
        bills.sort(Comparator.comparingDouble(Bill::getWithTaxAmount));

        return bills;
    }

    private static double calculatePlanA(Customer customer) {
        double callCost = customer.getCallMinutes() * PLAN_A_CALL_RATE;
        double smsCost = customer.getSmsCount() * PLAN_A_SMS_RATE;
        double internetCost = customer.getInternetUsage() * PLAN_A_INTERNET_RATE;
        return callCost + smsCost + internetCost;
    }

    private static double calculatePlanB(Customer customer) {
        double preTaxAmount = PLAN_B_FIXED_PRICE;

        if (customer.getCallMinutes() > PLAN_B_CALL_MINUTES) {
            double extraCallCost = (customer.getCallMinutes() - PLAN_B_CALL_MINUTES) * PLAN_A_CALL_RATE;
            preTaxAmount += extraCallCost;
        }

        if (customer.getSmsCount() > PLAN_B_SMS_COUNT) {
            double extraSmsCost = (customer.getSmsCount() - PLAN_B_SMS_COUNT) * PLAN_A_SMS_RATE;
            preTaxAmount += extraSmsCost;
        }

        if (customer.getInternetUsage() > PLAN_B_INTERNET_USAGE) {
            double extraInternetCost = (customer.getInternetUsage() - PLAN_B_INTERNET_USAGE) * PLAN_A_INTERNET_RATE;
            preTaxAmount += extraInternetCost;
        }

        return preTaxAmount;
    }

    private static double calculatePlanC(Customer customer) {
        double callCost = customer.getCallMinutes() * PLAN_A_CALL_RATE;
        double smsCost = customer.getSmsCount() * PLAN_A_SMS_RATE;
        double internetCost = customer.getInternetUsage() * PLAN_C_INTERNET_RATE;
        return PLAN_C_FIXED_PRICE + callCost + smsCost + internetCost;
    }

    private static double calculatePlanD(Customer customer) {
        double callCost = customer.getCallMinutes() * PLAN_D_CALL_RATE;
        double smsCost = customer.getSmsCount() * PLAN_D_SMS_RATE;
        return PLAN_D_FIXED_PRICE + callCost + smsCost;
    }

    static void writeOutputFile(List<Bill> bills) {
        try (PrintWriter writer = new PrintWriter("output.txt")) {
            for (Bill bill : bills) {
                writer.println("Customer Name: " + bill.getCustomer().getName());
                writer.println("Plan: " + bill.getCustomer().getPlan());
                writer.println("Pre-tax Amount: " + bill.getPreTaxAmount());
                writer.println("Tax: " + bill.getTax());
                writer.println("With-tax Amount: " + bill.getWithTaxAmount());
                writer.println();
            }

            double preTaxTotal = bills.stream().mapToDouble(Bill::getPreTaxAmount).sum();
            double taxTotal = bills.stream().mapToDouble(Bill::getTax).sum();
            double withTaxTotal = bills.stream().mapToDouble(Bill::getWithTaxAmount).sum();

            writer.println("Total Pre-tax Amount: " + preTaxTotal);
            writer.println("Total Tax: " + taxTotal);
            writer.println("Total With-tax Amount: " + withTaxTotal);
        } catch (FileNotFoundException e) {
            System.out.println("Error writing output file.");
        }
    }
}
