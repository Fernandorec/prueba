package online.alambritos.desktop_backoffice.parking.repository;

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot;
import online.alambritos.desktop_backoffice.parking.model.Ticket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ParkingRepository {

    @NotNull
    List<Ticket> getAllTickets();

    @NotNull
    List<ParkingSpot> getAllParkingSpots();

    @NotNull
    Ticket requireTicket(@NotNull String ticketId);

    @NotNull
    ParkingSpot requireParkingSpot(@NotNull String spotCode);
}