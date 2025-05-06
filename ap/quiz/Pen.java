package ap.quiz;

public class Pen extends Product {
    private String color;
    private String brand;
    public Pen(double price, String color, String brand , int discount) {
        super(price , discount);
        this.color = color;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "{ 'color': " + color + ", 'brand': " + brand + ", 'price': " + super.getPrice() + " }";
    }
}
