package online.alambritos.desktop_backoffice.parking.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    @NotNull private final String id;
    @NotNull private final String vehiclePlate;
    @NotNull private final String parkingSpotCode;
    @NotNull private final LocalDateTime entryTimestamp;

    @Nullable
    private LocalDateTime exitTimestamp;

    public Ticket(
            @NotNull String id,
            @NotNull String vehiclePlate,
            @NotNull String parkingSpotCode,
            @NotNull LocalDateTime entryTimestamp
    ) {
        this.id = id;
        this.vehiclePlate = vehiclePlate;
        this.parkingSpotCode = parkingSpotCode;
        this.entryTimestamp = entryTimestamp;
    }

    @NotNull
    public String getId() { return id; }

    @NotNull
    public String getVehiclePlate() { return vehiclePlate; }

    @NotNull
    public String getParkingSpotCode() { return parkingSpotCode; }

    @NotNull
    public LocalDateTime getEntryTimestamp() { return entryTimestamp; }

    @Nullable
    public LocalDateTime getExitTimestamp() { return exitTimestamp; }

    public boolean hasExited() { return exitTimestamp != null; }

    public void registerExit(@NotNull LocalDateTime timestamp) {
        if (hasExited()) {
            throw new IllegalStateException("El ticket ya tiene salida registrada.");
        }
        exitTimestamp = timestamp;
    }

    public long calculateStayMinutes() {
        LocalDateTime endTimestamp = exitTimestamp != null ? exitTimestamp : LocalDateTime.now();
        return Duration.between(entryTimestamp, endTimestamp).toMinutes();
    }
}