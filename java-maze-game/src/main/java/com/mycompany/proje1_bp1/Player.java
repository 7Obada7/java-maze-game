/**
* @file proje1_bp1
* @labirent keşif oyunu
* @assignment 1
* @25/12/2023
* @Obada Masri / obada.masri@stu.fsm.edu.tr
*/
package com.mycompany.proje1_bp1;

import java.util.Random;
import java.util.Scanner;

public class Player {

    private int x, y; // Oyuncunun mevcut konumu
    private char[] bonuses = new char[5]; // Toplanan bonuslar
    private int moveCount = 0; // Yapılan hamle sayısı

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public char[] getBonuses() {
        return bonuses;
    }

    public void makeMove(char[][] maze, String move) {
        if (move.equals("+")) {
            useBonus(maze);
        } else {
            int newX = x, newY = y;

            switch (move.toUpperCase()) {
                case "W":
                    newX--;
                    break;
                case "A":
                    newY--;
                    break;
                case "S":
                    newX++;
                    break;
                case "D":
                    newY++;
                    break;
                default:
                    System.out.println("Geçersiz hareket!");
                    return;
            }

            if (newX < 0 || newX >= maze.length || newY < 0 || newY >= maze[0].length) {
                System.out.println("Labirentin dışına çıkamazsınız!");
            } else if (maze[newX][newY] == '#') {
                encounterObstacle(maze, '#');
            } else {
                x = newX;
                y = newY;
                moveCount++;
                if (maze[x][y] == '!') {
                    encounterObstacle(maze, '!');
                } else if (maze[x][y] != '.') {
                    collectBonus(maze[x][y], maze, x, y);
                }
            }
        }
    }

    public void useBonus(char[][] maze) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Bonuses:");

        boolean foundBonuses = false;

        for (char bonus : bonuses) {
            if (bonus != '\0') {
                System.out.print(bonus + " ");
                foundBonuses = true;
            }
        }

        if (!foundBonuses) {
            System.out.println("No bonuses available!");
            return;
        }

        System.out.println("\nEnter the bonus to use: ");
        char selectedBonus = scanner.nextLine().charAt(0);

        switch (selectedBonus) {
            // Handle each bonus type case...
            case 'T':
                System.out.println("Enter the new X coordinate: ");
                int newX = scanner.nextInt();
                System.out.println("Enter the new Y coordinate: ");
                int newY = scanner.nextInt();
                teleport(maze, newX, newY);
                break;
            case 'R':
                removeWall(maze);
                break;
            case 'H':
                reduceMoveCount();
                System.out.println("Hamle sayısı 2 azaltıldı. Yeni hamle sayısı: " + moveCount);
                break;
            case 'F':
                defuseMine(maze);
                break;
            default:
                System.out.println("Belirtilen bonus envanterde bulunmuyor!");
                return;
        }

        System.out.println(selectedBonus + " bonusu kullanıldı ve envanterden kaldırıldı!");
    }

    private void removeBonus(char bonusToRemove) {
        for (int i = 0; i < bonuses.length; i++) {
            if (bonuses[i] == bonusToRemove) {
                bonuses[i] = '\0';
            }
        }
    }

    private void reduceMoveCount() {
        moveCount = Math.max(0, moveCount - 2);
    }

    private void removeWall(char[][] maze) {
        if (maze[x][y] == '#') {
            maze[x][y] = '.';
            System.out.println("Duvar kaldırıldı!");
        } else {
            System.out.println("Duvar yok!");
        }
    }

    private void defuseMine(char[][] maze) {
        boolean defused = false;

        for (int i = 0; i < bonuses.length; i++) {
            if (bonuses[i] == 'F') {
                maze[x][y] = '.';
                bonuses[i] = '\0';  // Remove only one 'F' bonus from the inventory
                moveCount += 5;
                defused = true;
                break;
            }
        }

        if (defused) {
            System.out.println("Mayın çözüldü! Hamle sayısı 5 arttı.");
        } else {
            // If 'F' bonus is not found, let the mine explode
            maze[x][y] = '.';
            moveCount += 5;
            System.out.println("Mayın patladı! Hamle sayısı 5 arttı.");
        }
    }

    public void encounterObstacle(char[][] maze, char obstacle) {
        if (obstacle == '#') {
            for (char bonus : bonuses) {
                if (bonus == 'R') {
                    removeWall(maze);
                    System.out.println("Duvar kaldırıldı!");
                    return;
                }
            }
            System.out.println("Duvar ile karşılaşıldı!");
        } else if (obstacle == '!') {
            for (char bonus : bonuses) {
                if (bonus == 'F') {
                    maze[x][y] = '.';
                    System.out.println("Mayın çözüldü!");
                    return;
                }
            }
            maze[x][y] = '.';
            moveCount += 5;
            System.out.println("Mayın patladı! Hamle sayısı 5 arttı.");
        }
    }

    private void collectBonus(char bonus, char[][] maze, int x, int y) {
        for (int i = 0; i < bonuses.length; i++) {
            if (bonuses[i] == '\0') {
                bonuses[i] = bonus;
                System.out.println(bonus + " bonusu toplandı!");
                maze[x][y] = '.';  // Update maze with '.' after collecting the bonus
                return;
            }
        }
        System.out.println("Bonus envanteri dolu!");
    }

    private void teleport(char[][] maze, int newX, int newY) {
        if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length) {
            if (maze[newX][newY] == '#' || maze[newX][newY] == '!') {
                System.out.println("Teleportation failed! There is an obstacle at the target location.");
            } else {
                System.out.println("Teleportation activated!");
                x = newX;
                y = newY;
                System.out.println("Yeni konum: (" + x + ", " + y + ")");
            }
        } else {
            System.out.println("Geçersiz koordinatlar! Teleportasyon başarısız.");
        }
    }

}
