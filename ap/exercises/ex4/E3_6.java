package ap.exercises.ex4;

public class E3_6 {
    private int hallwayLight;
    private int firstSwitch;
    private int secondSwitch;

    public E3_6(int firstSwitch, int secondSwitch) {
        this.firstSwitch = firstSwitch;
        this.secondSwitch = secondSwitch;
        this.hallwayLight = getLampState();
    }

    public int getSwitchState(int switchNum){
        if(switchNum == 1){ return firstSwitch; }
        else if(switchNum == 2){ return secondSwitch; }
        else {
            System.out.println("error");
            System.exit(0);
        }
        return -1;
    }

    public int getLampState(){ // 0 for off, 1 for on

        if(firstSwitch == secondSwitch){
            hallwayLight = 0;
        }else hallwayLight = 1;

        return hallwayLight;
    }
    public void toggleSwitch(int switchNum){
        if(switchNum == 1){
            if(firstSwitch == 1) firstSwitch = 0;
            else firstSwitch = 1;
        }
        else if(switchNum == 2){
            if(secondSwitch == 1) secondSwitch = 0;
            else secondSwitch = 1;
        }
        else {
            System.out.println("error");
        }
    }

    public String getSwitchesState(int state){
        if(state == 1) return "Up";
        return "Down";
    }
    public String getLightState(int state){
        if(state == 1) return "On";
        return "Off";
    }
    public void printState(E3_6 hallwayLight){
        System.out.println("Hallway Light : " + hallwayLight.getLightState((hallwayLight.getLampState())));
        System.out.println("First Switch : " + hallwayLight.getSwitchesState(hallwayLight.getSwitchState(1)));
        System.out.println("Second Switch : " + hallwayLight.getSwitchesState(hallwayLight.getSwitchState(2)));
    }

    public static void main(String[] args) {
        E3_6 hallwayLight = new E3_6(1, 1);

        hallwayLight.printState(hallwayLight);

        hallwayLight.toggleSwitch(1);
        System.out.println("\nSwitch toggle");
        hallwayLight.printState(hallwayLight);

        hallwayLight.toggleSwitch(2);
        System.out.println("\nSwitch toggle");
        hallwayLight.printState(hallwayLight);
    }
}
