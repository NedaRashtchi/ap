package ap.exercises.ex2;
import java.io.FileNotFoundException;
import java.util.*;
public class Main_EX2_PM_2_4 {

        public static void main(String[] args) throws FileNotFoundException {

            int k=9; //عدد مربوط به تمرین EX2_PM_1_1 و EX2_PM_1_2
            int c=15; //عدد مربوط به تمرین EX2_PM_1_3

            Random rnd = new Random();

            PacmanEngine pacmanEngine = new PacmanEngine(k,c);

            while(true) {
                pacmanEngine.printMatrix(); // مربوط به بخش آخر تمرین EX2_PM_1_3
                pacmanEngine.printScore(); // امتیاز تمرین EX2_PM_2_2
                pacmanEngine.printRemainTime(); // زمان تمرین EX2_PM_2_2

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                int direction=rnd.nextInt(4);
                pacmanEngine.move(direction);// حرکت نقطه خوار مربوط به تمرین EX2-PM.1.5
                pacmanEngine.save();
            }


    }



}
