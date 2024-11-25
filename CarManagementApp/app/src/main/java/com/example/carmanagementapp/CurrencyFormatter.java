package com.example.carmanagementapp;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount).replace("₫", "VND"); // Thay "₫" bằng "VND"
    }
}
