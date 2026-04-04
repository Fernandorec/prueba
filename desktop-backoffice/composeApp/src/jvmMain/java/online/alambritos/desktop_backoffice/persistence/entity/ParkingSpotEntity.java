package online.alambritos.desktop_backoffice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotTypeEntity type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotStatusEntity status = SpotStatusEntity.AVAILABLE;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SpotTypeEntity getType() {
        return type;
    }

    public void setType(SpotTypeEntity type) {
        this.type = type;
    }

    public SpotStatusEntity getStatus() {
        return status;
    }

    public void setStatus(SpotStatusEntity status) {
        this.status = status;
    }
}
