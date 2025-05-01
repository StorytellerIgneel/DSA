package dsa;

public class CargoWrapper {
    private Cargo cargo;
    private int quantity;

    public CargoWrapper(Cargo cargo, int quantity) {
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
