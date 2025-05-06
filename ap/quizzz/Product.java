package ap.quizzz;

public class Product {
    double price;
    double discount;
    public Product(double price , int discount) {
        this.price = price;
        this.discount =(double) discount/10;
    }

}
