package online.alambritos.desktop_backoffice.parking.service;

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot;
import online.alambritos.desktop_backoffice.parking.model.Ticket;
import online.alambritos.desktop_backoffice.parking.repository.ParkingRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingExitService {
    private final ParkingRepository repository;

    public ParkingExitService(@NotNull ParkingRepository repository) {
        this.repository = repository;
    }

    @NotNull
    public List<Ticket> getActiveTickets() {
        return repository.getAllTickets().stream()
                .filter(ticket -> !ticket.hasExited())
                .sorted(Comparator.comparing(Ticket::getEntryTimestamp))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @NotNull
    public List<Ticket> getCompletedTickets() {
        return repository.getAllTickets().stream()
                .filter(Ticket::hasExited)
                .filter(ticket -> ticket.getExitTimestamp() != null) // 🆕 evita null en sort
                .sorted(Comparator.comparing(Ticket::getExitTimestamp))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @NotNull
    public List<ParkingSpot> getParkingSpots() {
        return new ArrayList<>(repository.getAllParkingSpots());
    }

    public int getAvailableSpotCount() {
        return (int) repository.getAllParkingSpots().stream()
                .filter(spot -> !spot.isOccupied())
                .count();
    }

    @NotNull
    public Ticket registerExit(@NotNull String ticketId) {
        Ticket ticket = repository.requireTicket(ticketId);
        ticket.registerExit(LocalDateTime.now());
        repository.requireParkingSpot(ticket.getParkingSpotCode()).release();
        return ticket;
    }
}