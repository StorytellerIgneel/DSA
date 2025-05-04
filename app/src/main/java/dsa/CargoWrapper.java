package dsa;

public class CargoWrapper {
    private Cargo cargo;
    private int quantity;

    public CargoWrapper(Cargo cargo, int quantity) {
        if (cargo == null) {
            throw new IllegalArgumentException("Cargo cannot be null.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        this.cargo = cargo;
        this.quantity = quantity;
    }

    public Cargo getCargo() {
        return cargo;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void decrement() {
        if (quantity > 0) {
            quantity--;
        } else {
            throw new IllegalStateException("Cannot decrement quantity below zero.");
        }
    }

    @Override
    public String toString() {
        return "CargoWrapper{" +
                "cargo=" + cargo +
                ", quantity=" + quantity +
                '}';
    }
}
