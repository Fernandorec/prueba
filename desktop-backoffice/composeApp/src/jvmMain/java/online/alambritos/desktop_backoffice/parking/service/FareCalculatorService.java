package online.alambritos.desktop_backoffice.parking.service;

import online.alambritos.desktop_backoffice.parking.model.FareConfig;
import online.alambritos.desktop_backoffice.parking.model.Ticket;
import org.jetbrains.annotations.NotNull;

public class FareCalculatorService {

    public double calculate(@NotNull Ticket ticket) {
        long stayMinutes = ticket.calculateStayMinutes();

        // 1. El Salvador Rule: 15 min free
        if (stayMinutes <= FareConfig.FREE_GRACE_PERIOD_MINUTES) {
            return 0.00;
        }

        // 2. Pricing table
        if (stayMinutes <= 120) return FareConfig.RATE_UP_TO_2_HOURS;
        if (stayMinutes <= 180) return FareConfig.RATE_UP_TO_3_HOURS;
        if (stayMinutes <= 240) return FareConfig.RATE_UP_TO_4_HORAS;

        // 3. Additional hours after 4 hours
        long extraMinutes = stayMinutes - 240;
        long extraHours = (long) Math.ceil(extraMinutes / 60.0);

        return FareConfig.RATE_UP_TO_4_HORAS + (extraHours * FareConfig.ADDITIONAL_HOUR_RATE);
    }

    @NotNull
    public String buildBreakdown(@NotNull Ticket ticket) {
        long stayMinutes = ticket.calculateStayMinutes();
        double amount = calculate(ticket);

        return String.format(
                """
                ========== FARE BREAKDOWN ==========
                Ticket ID     : %s
                Vehicle       : %s
                Spot          : %s
                Stay          : %d min (%d h %d min)
                Grace period  : %d min
                ------------------------------------
                TOTAL DUE     : $%.2f
                ====================================
                """,
                ticket.getId(),
                ticket.getVehiclePlate(),
                ticket.getParkingSpotCode(),
                stayMinutes,
                stayMinutes / 60, stayMinutes % 60,
                FareConfig.FREE_GRACE_PERIOD_MINUTES,
                amount
        );
    }
}
