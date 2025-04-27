package ap.exercises.ex4;

public class E3_4 {
    private int hallwayLight;
    private int firstSwitch;
    private int secondSwitch;

    public E3_4(int firstSwitch, int secondSwitch) {
        this.firstSwitch = firstSwitch;
        this.secondSwitch = secondSwitch;
        this.hallwayLight = getLampState();
    }

    public int getFirstSwitchState() {// 0 for down, 1 for up
        return firstSwitch;
    }
    public int getSecondSwitchState(){
        return secondSwitch;
    }
    public int getLampState(){ // 0 for off, 1 for on

        if(firstSwitch == secondSwitch){
            hallwayLight = 0;
        }else hallwayLight = 1;

        return hallwayLight;
    }
    public void toggleFirstSwitch(){
        if(firstSwitch == 1) firstSwitch = 0;
        else firstSwitch = 1;
    }
    public void toggleSecondSwitch(){
        if(secondSwitch == 1) secondSwitch = 0;
        else secondSwitch = 1;
    }
    public String getSwitchState(int state){
        if(state == 1) return "Up";
        return "Down";
    }
    public String getLightState(int state){
        if(state == 1) return "On";
        return "Off";
    }
    public void printState(E3_4 hallwayLight){
        System.out.println("Hallway Light : " + hallwayLight.getLightState((hallwayLight.getLampState())));
        System.out.println("First Switch : " + hallwayLight.getSwitchState(hallwayLight.getFirstSwitchState()));
        System.out.println("Second Switch : " + hallwayLight.getSwitchState(hallwayLight.getSecondSwitchState()));
    }

    public static void main(String[] args) {
        E3_4 hallwayLight = new E3_4(1, 1);

        hallwayLight.printState(hallwayLight);

        hallwayLight.toggleFirstSwitch();
        System.out.println("\nSwitch toggle");
        hallwayLight.printState(hallwayLight);

        hallwayLight.toggleSecondSwitch();
        System.out.println("\nSwitch toggle");
        hallwayLight.printState(hallwayLight);
    }
}
