package bill;

import java.io.*;
import java.util.*;

class Customer {
    private String name;
    private String plan;
    private int callMinutes;
    private int smsCount;
    private int internetUsage;

    public Customer(String name, String plan, int callMinutes, int smsCount, int internetUsage) {
        this.name = name;
        this.plan = plan;
        this.callMinutes = callMinutes;
        this.smsCount = smsCount;
        this.internetUsage = internetUsage;
    }

    public String getName() {
        return name;
    }

    public String getPlan() {
        return plan;
    }

    public int getCallMinutes() {
        return callMinutes;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public int getInternetUsage() {
        return internetUsage;
    }
}

class Bill {
    private Customer customer;
    private double preTaxAmount;
    private double tax;
    private double withTaxAmount;

    public Bill(Customer customer, double preTaxAmount, double tax, double withTaxAmount) {
        this.customer = customer;
        this.preTaxAmount = preTaxAmount;
        this.tax = tax;
        this.withTaxAmount = withTaxAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPreTaxAmount() {
        return preTaxAmount;
    }

    public double getTax() {
        return tax;
    }

    public double getWithTaxAmount() {
        return withTaxAmount;
    }
}


