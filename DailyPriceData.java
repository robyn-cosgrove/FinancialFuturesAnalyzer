/**
 * DailyPriceData.java
 * Purpose: A simple immutable class to store the OHLCV (Open, High, Low, Close, Volume)
 * data for a single day of a futures contract.
 *
 * This demonstrates basic Java data structure design and encapsulation.
 */
public class DailyPriceData {
    private final String date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final int volume;

    /**
     * Constructor for DailyPriceData.
     */
    public DailyPriceData(String date, double open, double high, double low, double close, int volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    // --- Accessor Methods (Getters) ---
    public String getDate() {
        return date;
    }

    public double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return String.format("Date: %s | Close: $%.2f | Volume: %d", date, close, volume);
    }
}