package online.alambritos.desktop_backoffice.parking.service;

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot;
import online.alambritos.desktop_backoffice.parking.repository.ParkingSpotRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParkingSpotService {

    private final ParkingSpotRepository repository;

    public ParkingSpotService(@NotNull ParkingSpotRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new parking spot.
     * Called from Kotlin ViewModel with the code entered by the admin.
     *
     * @param code Unique identifier for the spot (e.g. "A-01", "B-12")
     */
    public void registerSpot(@NotNull String code) {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Spot code cannot be empty.");
        }
        ParkingSpot spot = new ParkingSpot(code.trim().toUpperCase());
        repository.save(spot);
    }

    @NotNull
    public List<ParkingSpot> getAllSpots() {
        return repository.findAll();
    }

    @NotNull
    public List<ParkingSpot> getAvailableSpots() {
        return repository.findAvailable();
    }
}
