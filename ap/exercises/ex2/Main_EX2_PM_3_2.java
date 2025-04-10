package ap.exercises.ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;


public class Main_EX2_PM_3_2 extends JFrame implements KeyListener {

      static Point pacman = new Point();
      static int width;
      static int height;
      static final int boxSize = 10;
      static int direction = 0;

    public Main_EX2_PM_3_2(int k) {
        addKeyListener(this);
        width = (k*boxSize)*5;
        height = (k*boxSize)*5;
        pacman.setLocation((width / boxSize) / 2, (height / boxSize) / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.clearRect(0, 0, width, height);
        drawPacman(g2D);
    }

    private void drawPacman(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(pacman.x * boxSize, pacman.y * boxSize, boxSize, boxSize);
    }

    public void move(int x, int y){
        pacman.setLocation(pacman.x + x, pacman.y + y);
        handleCrossBorder();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                move(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                move(1, 0);
                break;
            case KeyEvent.VK_DOWN:
                move(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                move(-1, 0);
                break;
            case KeyEvent.VK_Q:
                System.out.println("Exit");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid move");
                break;
        }
    }

    public static void handleCrossBorder() {
        if (pacman.x < 0) pacman.x = 0;
        if (pacman.x >= width / boxSize) pacman.x = (width / boxSize) - 1;
        if (pacman.y < 0) pacman.y = 0;
        if (pacman.y >= height / boxSize) pacman.y = (height / boxSize) - 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void main(String[] args) {
        System.out.print("Enter k : ");
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();

        Main_EX2_PM_3_2 frame = new Main_EX2_PM_3_2(k+2);
        frame.setVisible(true);

    }
}
