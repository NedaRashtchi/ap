package ap.exercises.ex4;

public class E3_5 {

    private E3_4 hallwayLight;

    public E3_5(int firstSwitch, int secondSwitch) {
        hallwayLight = new E3_4(firstSwitch, secondSwitch);
    }

    private void test() {
        System.out.println("Current State : ");
        hallwayLight.printState(hallwayLight);
        System.out.println("-------------------");
        System.out.println("Other possible states :\n");

        hallwayLight.toggleFirstSwitch();
        hallwayLight.printState(hallwayLight);
        System.out.println("-------------------");

        hallwayLight.toggleSecondSwitch();
        hallwayLight.printState(hallwayLight);
        System.out.println("-------------------");

        hallwayLight.toggleFirstSwitch();
        hallwayLight.printState(hallwayLight);
        System.out.println("-------------------");

    }

    public static void main() {
        E3_5 circuitTester = new E3_5(1, 0);
        circuitTester.test();
    }
}
