package online.alambritos.desktop_backoffice.parking.model;

public class FareConfig {

    public static final int FREE_GRACE_PERIOD_MINUTES = 15;

    public static final double RATE_UP_TO_2_HOURS = 0.50;
    public static final double RATE_UP_TO_3_HOURS = 1.00;
    public static final double RATE_UP_TO_4_HORAS = 2.00;
    public static final double ADDITIONAL_HOUR_RATE = 1.00;

    private FareConfig() {
        // Utility class - prevents instantiation
    }
}