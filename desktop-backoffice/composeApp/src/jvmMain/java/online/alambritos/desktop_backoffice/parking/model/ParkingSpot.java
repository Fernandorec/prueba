package online.alambritos.desktop_backoffice.parking.model;

import org.jetbrains.annotations.NotNull;

public class ParkingSpot {
    @NotNull private final String code;
    private boolean occupied;

    public ParkingSpot(@NotNull String code) {
        this.code = code;
    }

    @NotNull
    public String getCode() {
        return code;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        occupied = true;
    }

    public void release() {
        occupied = false;
    }
}