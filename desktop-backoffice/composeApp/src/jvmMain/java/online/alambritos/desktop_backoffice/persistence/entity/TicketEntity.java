package online.alambritos.desktop_backoffice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpotEntity spot;

    @Column(nullable = false)
    private OffsetDateTime entryAt;

    private OffsetDateTime exitAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatusEntity status = TicketStatusEntity.ACTIVE;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column(length = 3)
    private String currency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotEntity getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotEntity spot) {
        this.spot = spot;
    }

    public OffsetDateTime getEntryAt() {
        return entryAt;
    }

    public void setEntryAt(OffsetDateTime entryAt) {
        this.entryAt = entryAt;
    }

    public OffsetDateTime getExitAt() {
        return exitAt;
    }

    public void setExitAt(OffsetDateTime exitAt) {
        this.exitAt = exitAt;
    }

    public TicketStatusEntity getStatus() {
        return status;
    }

    public void setStatus(TicketStatusEntity status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
