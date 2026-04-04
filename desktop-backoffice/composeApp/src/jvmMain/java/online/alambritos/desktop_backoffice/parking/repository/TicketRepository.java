package online.alambritos.desktop_backoffice.parking.repository;

import online.alambritos.desktop_backoffice.parking.model.Ticket;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TicketRepository {

    private final List<Ticket> tickets = new ArrayList<>();

    public void save(@NotNull Ticket ticket) {
        tickets.add(ticket);
    }

    @NotNull
    public List<Ticket> findAll() {
        return Collections.unmodifiableList(tickets);
    }

    @NotNull
    public List<Ticket> findClosed() {
        return tickets.stream()
                .filter(Ticket::hasExited)
                .toList();
    }

    /**
     * Returns closed tickets whose exit date matches the given day.
     * Used by the ReportService for daily summaries.
     */
    @NotNull
    public List<Ticket> findClosedByDate(@NotNull LocalDate date) {
        return tickets.stream()
                .filter(Ticket::hasExited)
                .filter(t -> t.getExitTimestamp() != null &&
                        t.getExitTimestamp().toLocalDate().equals(date))
                .toList();
    }

    public Optional<Ticket> findOpenBySpotCode(@NotNull String spotCode) {
        return tickets.stream()
                .filter(t -> !t.hasExited())
                .filter(t -> t.getParkingSpotCode().equalsIgnoreCase(spotCode))
                .findFirst();
    }
}
