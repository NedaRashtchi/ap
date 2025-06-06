package ap.exercises.ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Main_EX2_PM_3_1 extends JFrame implements KeyListener {

    Point pacmanPoint = new Point();
    final int width = 300, height = 300, boxSize = 5;
    static int direction = 1;
    final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;
    Point dotPoint = new Point();
    static int score = 0;
    long start;

    public Main_EX2_PM_3_1() {
        addKeyListener(this);
        pacmanPoint.setLocation((width / boxSize) / 2, (height / boxSize) / 2);
        getNewDotPointLocation();
        setSize(width, height);
        start = System.currentTimeMillis();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.clearRect(0, 0, width, height);
        logic();
        drawPacman(g2D);
        drawDotPoint(g2D);
        drawScore(g2D);
        setVisible(true);
    }

    private void drawPacman(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(pacmanPoint.x * boxSize, pacmanPoint.y * boxSize, boxSize, boxSize);
    }

    private void drawDotPoint(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(dotPoint.x * boxSize, dotPoint.y * boxSize, boxSize, boxSize);
    }

    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        String s = "Score: " + score;
        g2d.drawString(s, 25, 50);

        long timeElapsed = System.currentTimeMillis() - start;
        long remainingTime = (60000 - timeElapsed) / 1000;
        String timeMessage = "Time Left: " + (remainingTime > 0 ? (long)remainingTime : 0) + "s";
        g2d.drawString(timeMessage, 25, 70);
    }

    private void logic() {
        long timeElapsed = System.currentTimeMillis() - start;
        if (timeElapsed >= 60000) {
            System.out.println("You are out of time");
            JOptionPane.showMessageDialog(this, "You are out of time", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        if (dotPoint.x == pacmanPoint.x && dotPoint.y == pacmanPoint.y) {
            score++;
            System.out.println("Score: " + score);

            if (score >= 10) {
                System.out.println("You Win");
                JOptionPane.showMessageDialog(this, "You reached maximum score", "You Win", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            getNewDotPointLocation();
        }
        movePacman();
    }

    private void movePacman() {
        int xMovement = 1;
        int yMovement = 0;
        switch (direction) {
            case LEFT:
                xMovement = -1;
                yMovement = 0;
                break;
            case RIGHT:
                xMovement = 1;
                yMovement = 0;
                break;
            case TOP:
                xMovement = 0;
                yMovement = -1;
                break;
            case BOTTOM:
                xMovement = 0;
                yMovement = 1;
                break;
            default:
                xMovement = yMovement = 0;
                break;
        }
        pacmanPoint.setLocation(pacmanPoint.x + xMovement, pacmanPoint.y + yMovement);
        handleCrossBorder();
    }

    private void getNewDotPointLocation() {
        Random rand = new Random();
        int delta = boxSize * 2;
        dotPoint.setLocation(rand.nextInt(width / boxSize - 2 * delta) + delta, rand.nextInt(height / boxSize - 2 * delta) + delta);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            direction = 3;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            direction = 4;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            direction = 1;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            direction = 2;
        else if (e.getKeyCode() == KeyEvent.VK_P)
            direction = 0;
        else if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
        else
            direction = -1;

        System.out.println("direction:" + direction + "    <- e.getKeyCode()=" + e.getKeyCode());

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void handleCrossBorder() {
        if (pacmanPoint.x < 0) pacmanPoint.x = 1;
        if (pacmanPoint.x >= width / boxSize) pacmanPoint.x = (width / boxSize) - 2;
        if (pacmanPoint.y < 0) pacmanPoint.y = 1;
        if (pacmanPoint.y >= height / boxSize) pacmanPoint.y = (height / boxSize) - 2;
    }


    public static void main(String[] args) {
        Main_EX2_PM_3_1 frame = new Main_EX2_PM_3_1();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}