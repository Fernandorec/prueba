package online.alambritos.desktop_backoffice.parking.model;

public class Car extends Vehicle {

    public Car(String plate) {
        super(plate);
    }

    @Override
    public String getType() {
        return "CAR";
    }
}