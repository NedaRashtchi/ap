package ap.quiz;

public class Book extends Product {
    private String name;

    public Book(double price, String name ,int discount) {
        super(price , discount);
        this.name = name;
    }
    @Override
    public String toString() {
        return "{ 'name': " + name + ", 'price': " + (price - (price* discount)) + " }";
    }
}
