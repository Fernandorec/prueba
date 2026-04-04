package online.alambritos.desktop_backoffice.parking.model;

public abstract class Vehicle {

    private String plate;

    public Vehicle(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    // Método abstracto que cada subclase implementa (polimorfismo)
    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " - Placa: " + plate;
    }
}