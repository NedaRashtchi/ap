package ap.exercises.ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class PacmanEngine {
    private int size, food;
    private long startTime;
    private char[][] matrix;
    private int score;
    private int pacmanX, pacmanY;

    public PacmanEngine(int k, int c) {
        size = k + 2;
        food = c;
        startTime = System.currentTimeMillis();
        matrix = new char[size][size];
        score = 0;
        pacmanX = (size - 1) / 2;
        pacmanY = (size - 1) / 2;
        initialize();
    }

    void initialize() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || j == size - 1 || i == size - 1) {
                    matrix[i][j] = '*';
                } else matrix[i][j] = ' ';
            }
        }
        matrix[pacmanY][pacmanX] = 'X';
        Random rand = new Random();
        int x, y;
        while (food != 0) {
            x = rand.nextInt(size - 1) + 1;
            y = rand.nextInt(size - 1) + 1;
            if (matrix[y][x] == ' ') {
                matrix[y][x] = '.';
                food--;
            }
        }
    }

    void printMatrix() {
        for (char[] y : matrix) {
            System.out.print(y);
            System.out.println();
        }
    }

    void printScore() {
        System.out.println("Score: " + score);
    }

    void printRemainTime() {
        long remain = (30000 - (System.currentTimeMillis() - startTime)) / 1000;
        System.out.println("Remaining time: " + remain);
        if (remain < 0) remain = 0;
    }

    void move(int direction) {

        switch (direction) {
            case 0:
                moves(0, -1);
                break;
            case 1:
                moves(1, 0);
                break;
            case 2:
                moves(0, 1);
                break;
            case 3:
                moves(-1, 0);
                break;
            default:
                break;
        }
        if (getWinScore() == 0) {
            System.out.println("Final score : " + score);
            return;
        }
        if (((30000 - (System.currentTimeMillis() - startTime)) / 1000) < 0)
            System.exit(0);

    }

    private void moves(int x, int y) {
        if (matrix[pacmanY + y][pacmanX + x] != '*') {
            if (matrix[pacmanY + y][pacmanX + x] == '.') {
                score++;
            }
            matrix[pacmanY + y][pacmanX + x] = 'X';
            matrix[pacmanY][pacmanX] = ' ';
            pacmanX += x;
            pacmanY += y;

        } else System.out.println("hitting the game wall");
    }

    private int getWinScore() {
        int count = 0;
        for (char[] row : matrix) {
            for (char c : row) {
                if (c == '.') {
                    count++;
                }
            }
        }
        return count;
    }
    void save() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("game.txt")) {
            writer.println(size);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.print(matrix[i][j]);
                }
                writer.println();
            }
            writer.println(score);
            //System.out.println("Game saved!");
        }
    }
    void read() throws FileNotFoundException {
        File file = new File("game.txt");
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        Scanner reader = new Scanner(file);
        size = Integer.parseInt(reader.nextLine());
        for (int i = 0; i < size; i++) {
            String line = reader.nextLine();
            for (int j = 0; j < size; j++) {
                matrix[i][j] = line.charAt(j);
                if (matrix[i][j] == 'X') {
                    pacmanX = j;
                    pacmanY = i;
                }
            }
        }
        score = Integer.parseInt(reader.nextLine());
        reader.close();
       // System.out.println("Game loaded successfully!");
    }
}