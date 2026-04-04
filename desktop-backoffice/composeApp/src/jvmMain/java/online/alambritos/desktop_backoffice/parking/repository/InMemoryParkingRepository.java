package online.alambritos.desktop_backoffice.parking.repository;

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot;
import online.alambritos.desktop_backoffice.parking.model.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryParkingRepository implements ParkingRepository {
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public InMemoryParkingRepository() {
        seedData();
    }

    @Override
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets); // 🆕 cambiado de List.copyOf()
    }

    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return new ArrayList<>(parkingSpots); // 🆕 cambiado de List.copyOf()
    }

    @Override
    public Ticket requireTicket(String ticketId) {
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(ticketId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado."));
    }

    @Override
    public ParkingSpot requireParkingSpot(String spotCode) {
        return parkingSpots.stream()
                .filter(spot -> spot.getCode().equals(spotCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado."));
    }

    private void seedData() {
        parkingSpots.add(new ParkingSpot("A1"));
        parkingSpots.add(new ParkingSpot("A2"));
        parkingSpots.add(new ParkingSpot("B1"));

        addActiveTicket("T-001", "P123-456", "A1", LocalDateTime.now().minusHours(2).minusMinutes(15));
        addActiveTicket("T-002", "M987-654", "A2", LocalDateTime.now().minusMinutes(48));
    }

    private void addActiveTicket(String id, String plate, String spotCode, LocalDateTime entryTimestamp) {
        tickets.add(new Ticket(id, plate, spotCode, entryTimestamp));
        requireParkingSpot(spotCode).occupy();
    }
}