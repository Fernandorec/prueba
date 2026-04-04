package online.alambritos.desktop_backoffice.parking.repository;

import online.alambritos.desktop_backoffice.parking.model.ParkingSpot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ParkingSpotRepository {

    private final List<ParkingSpot> spots = new ArrayList<>();

    public void save(@NotNull ParkingSpot spot) {
        boolean alreadyExists = spots.stream()
                .anyMatch(s -> s.getCode().equalsIgnoreCase(spot.getCode()));
        if (alreadyExists) {
            throw new IllegalArgumentException("Spot with code " + spot.getCode() + " already exists.");
        }
        spots.add(spot);
    }

    @NotNull
    public List<ParkingSpot> findAll() {
        return Collections.unmodifiableList(spots);
    }

    @NotNull
    public List<ParkingSpot> findAvailable() {
        return spots.stream()
                .filter(s -> !s.isOccupied())
                .toList();
    }

    @NotNull
    public Optional<ParkingSpot> findByCode(@NotNull String code) {
        return spots.stream()
                .filter(s -> s.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
