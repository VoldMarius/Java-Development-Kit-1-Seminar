package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;
    private static final Random RANDOM = new Random();
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int mode;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private char[][] field;
    private final int DOT_PADDING = 5;
    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";
    private boolean isGameOver;
    private boolean isInitialized;
    Сhecker  checkWin = new Сhecker(fieldSizeX,winLength);

    Map(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                update(e);
            }
        });
        isInitialized = false;
    }
    private void update(MouseEvent e){
        if(isGameOver || !isInitialized) return;
        int cellX = e.getX()/cellWidth;
        int cellY = e.getY()/cellHeight;
        if(!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
        System.out.printf("x= %d, y= %d", cellX, cellY);
        if(checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        aiTurn();;
        repaint();
        if(checkEndGame(AI_DOT, STATE_WIN_AI)) return;
    }
    public void startNewGame(int mode, int fSzX, int fSzY, int wLen){
        this.mode = mode;
        fieldSizeX = fSzX;
        fieldSizeY = fSzY;
        winLength = wLen;
        initMap();
        isGameOver = false;
        isInitialized = true;
        repaint();
        System.out.printf("Mode: %d;\nSise: x=%d, y=%d;\nWinLength: %d", mode, fSzX, fSzY, wLen);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g){
        if(!isInitialized) return;
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellWidth = panelWidth/fieldSizeX;
        cellHeight = panelHeight/fieldSizeY;
        g.setColor(Color.BLACK);

        for (int h = 0; h < fieldSizeY; h++) {
            int y = h*cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }
        for (int w = 0; w < fieldSizeX; w++) {
            int x = w*cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }
        for(int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++) {
                if(field[y][x] == EMPTY_DOT) continue;
                if(field[y][x] == HUMAN_DOT){
                    g.setColor(Color.BLUE);
                    g.fillOval(x*cellWidth + DOT_PADDING,
                            y*cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING*2,
                            cellHeight- DOT_PADDING*2);
                }else if(field[y][x] == AI_DOT){
                    g.setColor(Color.RED);
                    g.fillOval(x*cellWidth + DOT_PADDING,
                            y*cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING*2,
                            cellHeight- DOT_PADDING*2);
                }else throw new RuntimeException("Непонятное значение" + field[y][x] +
                        "в ячейке: x = " + x+" , y= " + y);
            }
        }
        if(isGameOver) showMessageGameOver(g);
    }
    private void initMap(){
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private boolean isValidCell(int x, int y){
        return x>=0 && x<fieldSizeX && y>=0 && y<fieldSizeY;
    }
    private boolean isEmptyCell(int x, int y){
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn(){
        int x, y;
        do{
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while(!isEmptyCell(x,y));
        field[y][x] = AI_DOT;
    }
    private boolean isMApFull(){
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if(field[i][j]==EMPTY_DOT) return false;
            }
        }
        return true;
    }

    private boolean checkEndGame(int dot, int gameOverType){
        if(checkWin.isWinningMove(fieldSizeX,fieldSizeY, (char) dot)){
            this.gameOverType = gameOverType;
            isGameOver=true;
            repaint();
            return true;
        }
        if(isMApFull()){
            this.gameOverType = STATE_DRAW;
            repaint();
            return true;
        }
        return false;
    }

//    private boolean checkWin(int dot){
//
////        Проверяем строки
//        for (int row = 0; row < fieldSizeY; row++) {
//            for (int i = 0; i <= fieldSizeX-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[row][i+j]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
//
////        Проверяем столбцы
//        for (int column = 0; column < fieldSizeY; column++) {
//            for (int i = 0; i <= fieldSizeY-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[i+j][column]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
//
////        Проверяем правые диоганали
////        Начало диагонали по первому столбцу
//        for (int row = 0; row <= fieldSizeY-winLength; row++) {
//            for (int i = 0; i <= fieldSizeY-row-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[row+i+j][i+j]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
////        Начало диагонали по первой строке
//        for (int column = 1; column <= fieldSizeX-winLength; column++) {
//            for (int i = 0; i <= fieldSizeX-column-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[i+j][column+i+j]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
//
////        Проверяем левые диоганали
////        Начало диагонали по первому столбцу
//        for (int row = winLength-1; row < fieldSizeY; row++) {
//            for (int i = 0; i <= row+1-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[row-i-j][i+j]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
////        Начало диагонали по последней строке
//        for (int column = 1; column <= fieldSizeX-winLength; column++) {
//            for (int i = 0; i <= fieldSizeX-column-winLength; i++) {
//                boolean isWin = true;
//                for (int j = 0; j < winLength; j++) {
//                    if(field[fieldSizeX-1-i-j][column+i+j]!=dot) {
//                        isWin = false;
//                    }
//                }
//                if(isWin) return true;
//            }
//        }
////        if(field[0][0] == dot && field[0][1] == dot &&  field[0][2] == dot) return true;
////        if(field[1][0] == dot && field[1][1] == dot &&  field[1][2] == dot) return true;
////        if(field[2][0] == dot && field[2][1] == dot &&  field[2][2] == dot) return true;
////
////        if(field[0][0] == dot && field[1][0] == dot &&  field[2][0] == dot) return true;
////        if(field[0][1] == dot && field[1][1] == dot &&  field[2][1] == dot) return true;
////        if(field[0][2] == dot && field[1][2] == dot &&  field[2][2] == dot) return true;
////
////        if(field[0][0] == dot && field[1][1] == dot &&  field[2][2] == dot) return true;
////        if(field[0][2] == dot && field[1][1] == dot &&  field[2][0] == dot) return true;
//        return false;
//    }
    private void showMessageGameOver(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,getHeight()/2-35,getWidth(),70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD,25));
        switch (gameOverType){
            case STATE_DRAW:
                g.drawString(MSG_DRAW, 100,getHeight()/2); break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI, 20,getHeight()/2); break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN, 60,getHeight()/2); break;
            default:
                throw new RuntimeException("Неизвестное состояние окончания игры: " + gameOverType);
        }
    }
}

