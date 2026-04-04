package online.alambritos.desktop_backoffice.parking.model;

public class VehicleFactory {

    // Recibe el tipo como String (lo que manda tu ViewModel desde Kotlin)
    public static Vehicle create(String plate, String type) {
        return switch (type) {
            case "CAR" -> new Car(plate);
            case "MOTORCYCLE" -> new Motorcycle(plate);
            default -> throw new IllegalArgumentException("Tipo desconocido: " + type);
        };
    }
}