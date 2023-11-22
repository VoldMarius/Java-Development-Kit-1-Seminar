package JFrame;

public class Сhecker {
    private char[][] board;
    private int size;
    private int winLength;

    public Сhecker(int size, int winLength) {
        this.size = size;
        this.winLength = winLength;
        board = new char[size][size];
    }

    public boolean isWinningMove(int row, int col, char player) {
        // Проверка по горизонтали
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (board[row][i] == player) {
                count++;
                if (count == winLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Проверка по вертикали
        count = 0;
        for (int i = 0; i < size; i++) {
            if (board[i][col] == player) {
                count++;
                if (count == winLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Проверка по главной диагонали
        count = 0;
        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        for (int i = 0; i < size - Math.max(row, col); i++) {
            if (board[startRow + i][startCol + i] == player) {
                count++;
                if (count == winLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Проверка по побочной диагонали
        count = 0;
        startRow = row + Math.min(row, size - 1 - col);
        startCol = col - Math.min(row, size - 1 - col);
        for (int i = 0; i < size - Math.max(row, size - 1 - col); i++) {
            if (board[startRow - i][startCol + i] == player) {
                count++;
                if (count == winLength) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }
}