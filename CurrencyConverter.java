import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {

    
    private static double getExchangeRate(String baseCurrency, String targetCurrency) {
        
        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("USD_TO_EUR", 0.85);
        exchangeRates.put("EUR_TO_USD", 1.18);
        exchangeRates.put("USD_TO_INR", 74.50);
        exchangeRates.put("INR_TO_USD", 0.013);
        exchangeRates.put("EUR_TO_INR", 87.50);
        exchangeRates.put("INR_TO_EUR", 0.0114);

        String key = baseCurrency + "_TO_" + targetCurrency;
        return exchangeRates.getOrDefault(key, 1.0); 
    }

    
    private static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    
    private static void displayResult(double convertedAmount, String targetCurrency) {
        System.out.printf("Converted Amount: %.2f %s%n", convertedAmount, targetCurrency);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Enter base currency (e.g., USD, EUR, INR): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g., USD, EUR, INR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        
        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        
        double convertedAmount = convertCurrency(amount, exchangeRate);

        
        displayResult(convertedAmount, targetCurrency);
    }
}
