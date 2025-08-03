/**
* @file proje1_bp1
* @labirent keşif oyunu
* @assignment 1
* @25/12/2023
* @Obada Masri / obada.masri@stu.fsm.edu.tr
*/

package com.mycompany.proje1_bp1;


import com.mycompany.proje1_bp1.Player;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        char[][] labirent = {
            {'#', '!', '.', '.', 'R', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'},
            {'.', '.', '#', '.', '.', '.', '#', '.', 'H', '.', '.', '.', '.', '.', '!'},
            {'F', '.', '.', '.', '#', '.', '!', '.', '.', 'R', '.', '.', '#', '#', '.'},
            {'.', '.', '#', '.', '.', '#', '.', '.', '.', '.', 'F', '.', '.', '.', '.'},
            {'.', '!', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '!', '.', '.', 'H', '.', '.', 'F', '.', '.', 'R'},
            {'#', '#', '#', '#', '.', '.', '#', '.', '.', '.', 'T', '.', '.', '.', 'E'},
            {'.', '.', '#', '.', 'F', '.', '#', '#', '.', '#', '#', '#', '#', '.', '.'},
            {'.', '#', '.', '.', '.', '.', '!', '.', '#', '.', '.', '.', '#', '.', '.'},
            {'.', 'T', 'T', '.', '#', '#', '.', '.', '.', '.', 'T', '.', '.', '.', 'R'},
            {'.', '.', '.', '#', '.', '.', '.', '#', '.', '#', '.', '#', '.', 'T', '.'},
            {'B', '.', '#', '.', '.', '!', '.', '!', '.', '.', '.', '.', '.', '.', '#'},
            {'.', '.', '.', 'F', '!', '.', '.', '.', 'H', '.', '.', 'R', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '.', '!', '.', '.', '.', '#', '.', '.', '#', '.'},
            {'.', '.', '.', '#', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '#'}
        };

        Player player = initializePlayer(labirent);

        Scanner scanner = new Scanner(System.in);
        String move;

        int moveCounter = 0;
        final int MOVE_INTERVAL = 5;

        while (true) {
            printMaze(labirent, player.getX(), player.getY(), player.getMoveCount());
            System.out.println("Hamle: W(Above), A(Left), S(Below), D(Right), +(Use Bonus)");
            move = scanner.nextLine();
            player.makeMove(labirent, move);

            moveCounter++;

            if (moveCounter % MOVE_INTERVAL == 0) {
                moveBonusesAndMines(labirent);
            }

            if (labirent[player.getX()][player.getY()] == 'E') {
                System.out.println("Oyunu tamamladınız! Toplam hamle sayısı: " + player.getMoveCount());
                break;
            }
        }
    }

    private static void moveBonusesAndMines(char[][] maze) {
        moveBonuses(maze);
        moveMines(maze);
    }

    private static void moveBonuses(char[][] maze) {
        Random random = new Random();
        for (char bonus : new char[]{'T', 'R', 'H', 'F'}) {
            placeRandomly(maze, bonus, random);
        }
    }

    private static void moveMines(char[][] maze) {
        Random random = new Random();
        for (char mine : new char[]{'T', 'R', 'H', 'F', '#'}) {
            placeRandomly(maze, mine, random);
        }
    }

    private static void placeRandomly(char[][] maze, char character, Random random) {
        int count = 0;
        int maxAttempts = 100;  // Adjust as needed to avoid infinite loop

        while (count < 5) {
            int x = random.nextInt(maze.length);
            int y = random.nextInt(maze[0].length);

            if (isValidPlacement(maze, x, y, character)) {
                maze[x][y] = character;
                count++;
            }

            maxAttempts--;
            if (maxAttempts <= 0) {
                // To prevent infinite loop
                System.out.println("Could not place " + count + " instances of " + character + ". Max attempts reached.");
                break;
            }
        }
    }

    private static boolean isValidPlacement(char[][] maze, int x, int y, char character) {
        return maze[x][y] == '.' && !isAtStartOrEnd(maze, x, y) && !isAtPlayerPosition(maze, x, y) && !isAtBonusOrMine(maze, x, y, character);
    }

    private static boolean isAtStartOrEnd(char[][] maze, int x, int y) {
        return maze[x][y] == 'B' || maze[x][y] == 'E';
    }

    private static boolean isAtPlayerPosition(char[][] maze, int x, int y) {
        return maze[x][y] == '*';
    }

    private static boolean isAtBonusOrMine(char[][] maze, int x, int y, char character) {
        return maze[x][y] == 'T' || maze[x][y] == 'R' || maze[x][y] == 'H' || maze[x][y] == 'F' || maze[x][y] == '#' || maze[x][y] == '!';
    }

    private static void printMaze(char[][] maze, int playerX, int playerY, int stepCount) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print('*' + " ");
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Adım Sayısı: " + stepCount);
        System.out.println("Bulunduğunuz konum: (" + playerX + ", " + playerY + ")");
    }

    private static Player initializePlayer(char[][] maze) {
        int startX = -1, startY = -1;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'B') {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }

        if (startX == -1 || startY == -1) {
            throw new IllegalStateException("Player starting point not found in the maze.");
        }

        return new Player(startX, startY);
    }
}
