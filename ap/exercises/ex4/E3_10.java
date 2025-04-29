package ap.exercises.ex4;

import java.util.ArrayList;

public class E3_10 {

    private static class Product {
        private final String name;
        private final double price;

        public Product(String name, double price){
            this.name = name;
            this.price = price;
        }
    }
    ArrayList<Product> products ;

    public E3_10(){

       products = new ArrayList<>();
    }
    private void addProduct(Product product){
        products.add(product);
    }
    private String printReceipt(){
        String receipt = "Purchased items:\n\n";
        double totalPrice = 0;
        for (Product p : products) {
//            receipt = receipt.concat(p.name + "\t" + String.valueOf(p.price) + "\n");
            receipt = receipt.concat(p.name + "\t" + p.price + "\n");
            totalPrice += p.price;
        }
        return receipt.concat("-----------"+"\nTotal Price: " + totalPrice + "\n"); //String.valueOf(totalPrice)
    }

    public static void main() {
        E3_10 cashRegister = new E3_10();
        cashRegister.addProduct(new Product("milk",20));
        cashRegister.addProduct(new Product("coffee",15.59));
        cashRegister.addProduct(new Product("tea ",10.29));
        cashRegister.addProduct(new Product("masala",22));

        System.out.println(cashRegister.printReceipt());

    }
}
