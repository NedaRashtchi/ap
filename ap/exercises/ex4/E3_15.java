package ap.exercises.ex4;

public class E3_15 {
    private final String from;
    private final String to;
    private String body = "";

    public E3_15(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void addLine(String line) {
        body = body.concat(line + "\n");
    }
    public String getText() {
        return "Dear " + to + ":\n\n" + body + "\nSincerely,\n" + from;
    }
}
