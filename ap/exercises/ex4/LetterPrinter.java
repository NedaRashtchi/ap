package ap.exercises.ex4;

public class LetterPrinter {
    public static void printLetter(E3_15 letter) {
        System.out.println(letter.getText());
    }

    public static void main() {
        E3_15 letter = new E3_15("Mary", "John");
        letter.addLine("I am sorry we must part.");
        letter.addLine("I wish you all the best.");

        LetterPrinter.printLetter(letter);
    }
}
