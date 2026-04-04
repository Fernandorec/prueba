package online.alambritos.desktop_backoffice.parking.service;

import online.alambritos.desktop_backoffice.parking.model.Ticket;
import online.alambritos.desktop_backoffice.parking.repository.TicketRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportService {

    private final TicketRepository ticketRepository;
    private final FareCalculatorService fareCalculatorService;

    public ReportService(
            @NotNull TicketRepository ticketRepository,
            @NotNull FareCalculatorService fareCalculatorService
    ) {
        this.ticketRepository = ticketRepository;
        this.fareCalculatorService = fareCalculatorService;
    }

    /**
     * Calculates total revenue collected on a specific date.
     *
     * @param date The day to report on.
     * @return Sum of all fares from closed tickets that day.
     */
    public double calculateDailyRevenue(@NotNull LocalDate date) {
        List<Ticket> closedTickets = ticketRepository.findClosedByDate(date);
        return closedTickets.stream()
                .mapToDouble(fareCalculatorService::calculate)
                .sum();
    }

    /**
     * Builds a formatted daily report string for console display.
     * Called from the Kotlin ViewModel — just render the returned String.
     *
     * @param date The day to report on.
     * @return Formatted multi-line report.
     */
    @NotNull
    public String buildDailyReport(@NotNull LocalDate date) {
        List<Ticket> closedTickets = ticketRepository.findClosedByDate(date);
        double totalRevenue = calculateDailyRevenue(date);

        StringBuilder sb = new StringBuilder();
        String dateFormatted = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        sb.append("============================================\n");
        sb.append("         DAILY INCOME REPORT               \n");
        sb.append("  Date: ").append(dateFormatted).append("\n");
        sb.append("============================================\n");

        if (closedTickets.isEmpty()) {
            sb.append("  No closed tickets found for this date.\n");
        } else {
            sb.append(String.format("  %-12s %-10s %-8s %s%n",
                    "Ticket ID", "Vehicle", "Spot", "Amount"));
            sb.append("  ------------------------------------------\n");

            for (Ticket ticket : closedTickets) {
                double amount = fareCalculatorService.calculate(ticket);
                sb.append(String.format("  %-12s %-10s %-8s $%.2f%n",
                        ticket.getId(),
                        ticket.getVehiclePlate(),
                        ticket.getParkingSpotCode(),
                        amount));
            }
        }

        sb.append("============================================\n");
        sb.append(String.format("  TOTAL COLLECTED : $%.2f%n", totalRevenue));
        sb.append("  Tickets closed  : ").append(closedTickets.size()).append("\n");
        sb.append("============================================\n");

        return sb.toString();
    }
}
