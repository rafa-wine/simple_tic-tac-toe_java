package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String inicialField = "_________";
        char[][] field = new char[3][3];
        boolean[][] status = new boolean[4][2];

        createFieldMatrix(inicialField, field, true);
        createFieldMatrix(inicialField, field, false);

        boolean exit = true;
        boolean whoIsItTurn = true;

        do {
            coordinates(field, whoIsItTurn);
            whoIsItTurn = !whoIsItTurn;

            countingTurns(field, status);
            threeTimesHorAndVer(field, false, status);
            threeTimesHorAndVer(field, true, status);
            threeTimesDiag(field, status);

            if (status[0][1] && !status[1][0] && !status[1][1] && !status[2][0] && !status[2][1] && !status[3][0] && !status[3][1]) {
                System.out.print("Draw");
                exit = false;
            } else {
                if (status[1][0] || status[1][1]) {
                    System.out.print(status[1][0] ? "X wins" : "O wins");
                    exit = false;
                }
                if (status[2][0] || status[2][1]) {
                    System.out.print(status[2][0] ? "X wins" : "O wins");
                    exit = false;

                }
                if (status[3][0] || status[3][1]) {
                    System.out.print(status[3][0] ? "X wins" : "O wins");
                    exit = false;
                }
            }

        } while (exit);
    }

    public static void createFieldMatrix(String inicialField, char[][] field, boolean fillMatrix) {
        int aux = 0;
        String matrix = "";

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (fillMatrix) {
                    field[i][j] = 95 == inicialField.charAt(aux) ? 32 : inicialField.charAt(aux);
                } else {
                    matrix += j == 0 ? String.format("| %c ", field[i][j]) :
                            j == 2 ? String.format("%c |%n", field[i][j]) :
                                    String.format("%c ", field[i][j]);
                }
                aux += 1;
            }
        }

        System.out.print(!fillMatrix ? String.format("---------%n%s---------%n", matrix) : "");
    }

    public static void coordinates(char[][] field, boolean isItX) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int coordinateX;
        int coordinateY;

        do {
            input = scanner.nextLine().replaceAll(" ", "");;

            if (input.matches("\\d+")) {
                coordinateX = input.charAt(0) - '0';
                coordinateY = input.charAt(1) - '0';

                if (coordinateX > 3 || coordinateY > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    coordinateX -= 1;
                    coordinateY -= 1;

                    if (field[coordinateX][coordinateY] != 'X' && field[coordinateX][coordinateY] != 'O') {
                        field[coordinateX][coordinateY] = isItX ? 'X' : 'O';
                        createFieldMatrix(null, field, false);
                        break;
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                }
            } else {
                System.out.println("You should enter numbers!");
            }

        } while(true);
    }

    public static void countingTurns(char[][] field, boolean[][] status) {
        int turnsX = 0;
        int turnsO = 0;
        boolean[] possible = {false, false};

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                turnsX += field[i][j] == 'X' ? 1 : 0;
                turnsO += field[i][j] == 'O' ? 1 : 0;
            }
        }

        possible[0] = turnsX == turnsO ? true : turnsX - turnsO == 1 || turnsX - turnsO == -1 ? true : false;
        possible[1] = turnsX + turnsO == 9 ? true : false;

        status[0] = possible;
    }

    public static void threeTimesHorAndVer(char[][] field, boolean transposed, boolean[][] status) {
        char[] players = {'X', 'O'};
        boolean[] wins = {false, false};
        int count = 0;

        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field.length; k++) {
                    if (transposed) {
                        count += players[i] == field[k][j] ? 1 : 0;
                    } else {
                        count += players[i] == field[j][k] ? 1 : 0;
                    }
                }
                if (count == 3) {
                    wins[i] = true;
                    count = 0;
                    break;
                } else {
                    wins[i] = false;
                    count = 0;
                }
            }
        }

        if (transposed) {
            status[2] = wins;
        } else {
            status[1] = wins;
        }
    }

    public static void threeTimesDiag(char[][] field, boolean[][] status) {
        char[] players = {'X', 'O'};
        boolean[] wins = {false, false};
        int countNormal = 0;
        int countInvert = 0;

        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field[j].length; k++) {
                    if (j == k) {
                        countNormal += players[i] == field[j][k] ? 1 : 0;
                    }
                    if (j == (field[j].length - 1 - k)) {
                        countInvert += players[i] == field[j][k] ? 1 : 0;
                    }
                }
            }
            if (countNormal == 3 || countInvert == 3) {
                wins[i] = true;
                countNormal = 0;
                countInvert = 0;
            } else {
                wins[i] = false;
                countNormal = 0;
                countInvert = 0;
            }
        }
        status[3] = wins;
    }
}
