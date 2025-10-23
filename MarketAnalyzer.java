import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MarketAnalyzer.java
 * Purpose: The core logic class. It simulates the loading of futures market data
 * (CME's main product), performs basic analysis, and prints a structured report.
 *
 * This demonstrates strong Object-Oriented Programming (OOP) principles like
 * separation of concerns and method encapsulation.
 */
public class MarketAnalyzer {
    private final String symbol; // e.g., "ZC" for Corn Futures
    private final List<DailyPriceData> prices;
    private final Random random;

    /**
     * Constructor: Initializes the analyzer and loads data immediately.
     * @param symbol The ticker symbol of the futures contract.
     */
    public MarketAnalyzer(String symbol) {
        this.symbol = symbol;
        this.prices = new ArrayList<>();
        this.random = new Random();
        this.loadSampleData();
    }

    /**
     * Simulates loading a month's worth of historical price data.
     * In a real application, this would read from a database or file (like CSV).
     */
    private void loadSampleData() {
        System.out.println("Loading 30 days of sample data for " + this.symbol + "...");

        // Simulate daily prices around a starting value of 500
        double currentClose = 500.00;

        for (int i = 0; i < 30; i++) {
            // Simulate realistic price movement
            double openPrice = currentClose + (random.nextDouble() * 2.0 - 1.0); // +/- 1.0 change from previous close
            double closeChange = random.nextDouble() * 5.0 - 2.5; // +/- 2.5 daily swing
            double closePrice = openPrice + closeChange;

            // Ensure high/low bound the open and close
            double highPrice = Math.max(openPrice, closePrice) + random.nextDouble() * 0.5;
            double lowPrice = Math.min(openPrice, closePrice) - random.nextDouble() * 0.5;

            // Volume simulation (relevant metric for CME's business)
            int volume = 1000 + random.nextInt(4000); // Volume between 1000 and 5000

            String dateStr = String.format("2025-10-%02d", 1 + i); // Dates from 2025-10-01 to 2025-10-30

            DailyPriceData dataPoint = new DailyPriceData(dateStr, openPrice, highPrice, lowPrice, closePrice, volume);
            this.prices.add(dataPoint);
            currentClose = closePrice; // Set the current close for the next day's simulation
        }
        System.out.println("Successfully loaded " + this.prices.size() + " data points.");
    }

    /**
     * Calculates the average closing price over the entire data set.
     * @return The average closing price.
     */
    public double calculateAveragePrice() {
        if (prices.isEmpty()) {
            return 0.0;
        }
        double totalClose = 0.0;
        for (DailyPriceData data : prices) {
            totalClose += data.getClose();
        }
        return totalClose / prices.size();
    }

    /**
     * Finds the day with the highest trading volume.
     * Volume is critical for exchanges like CME.
     * @return The DailyPriceData object for the day with the highest volume, or null if empty.
     */
    public DailyPriceData findMaxVolumeDay() {
        if (prices.isEmpty()) {
            return null;
        }

        DailyPriceData maxVolumeDay = prices.get(0);
        for (DailyPriceData data : prices) {
            if (data.getVolume() > maxVolumeDay.getVolume()) {
                maxVolumeDay = data;
            }
        }
        return maxVolumeDay;
    }

    /**
     * Executes all analyses and prints a clean, organized report to the console.
     */
    public void runAnalysis() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Futures Contract Analysis Report: " + symbol);
        System.out.println("=".repeat(60));

        // 1. Price Level Check
        double avgPrice = calculateAveragePrice();
        System.out.printf("| Contract Ticker: %s (Simulated Corn Futures)%n", this.symbol);
        System.out.printf("| Data Range: %s to %s%n", this.prices.get(0).getDate(), this.prices.get(this.prices.size() - 1).getDate());
        System.out.printf("| Average Closing Price: $%.2f%n", avgPrice);

        // 2. Volatility Check (Price Swing)
        double firstPrice = this.prices.get(0).getClose();
        double lastPrice = this.prices.get(this.prices.size() - 1).getClose();
        double swing = lastPrice - firstPrice;

        System.out.printf("| Total Price Change (Swing): $%.2f%n", swing);

        // 3. Volume Check
        DailyPriceData maxVolDay = findMaxVolumeDay();
        if (maxVolDay != null) {
            System.out.printf("| Highest Volume Day: %s (Volume: %,d)%n", maxVolDay.getDate(), maxVolDay.getVolume());
            System.out.printf("| Closing Price on Max Volume Day: $%.2f%n", maxVolDay.getClose());
        }

        System.out.println("=".repeat(60) + "\n");
    }

    // --- Main Method to run the application ---
    public static void main(String[] args) {
        // Create an instance of the MarketAnalyzer for Corn Futures (ZC)
        MarketAnalyzer cornAnalyzer = new MarketAnalyzer("ZC");

        // Execute the analysis and print the report
        cornAnalyzer.runAnalysis();
    }
}