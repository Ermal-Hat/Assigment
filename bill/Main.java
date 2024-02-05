package bill;

import java.util.*;

import static bill.MobileBillingSystem.*;

public class Main {
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

}

