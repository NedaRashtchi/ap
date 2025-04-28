package ap.exercises.ex4;

public class E3_5 {

    private E3_4 hallwayLight;

    public E3_5(int firstSwitch, int secondSwitch) {
        hallwayLight = new E3_4(firstSwitch, secondSwitch);
    }
//    private boolean check(){
//        if(hallwayLight.getFirstSwitchState() == hallwayLight.getSecondSwitchState()){
//            if(hallwayLight.getLampState() == 0) return true;
//            else return false;
//        }else {
//            if(hallwayLight.getLampState() == 0) return false;
//            else return true;
//        }
//    }
    private void test(){
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

    public static void main(String[] args) {
        E3_5 circuitTester = new E3_5(1, 0);
        circuitTester.test();
    }
}
