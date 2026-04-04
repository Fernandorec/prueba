package online.alambritos.desktop_backoffice.parking.model;

public class Motorcycle extends Vehicle {

    public Motorcycle(String plate) {
        super(plate);
    }

    @Override
    public String getType() {
        return "MOTORCYCLE";
    }
}