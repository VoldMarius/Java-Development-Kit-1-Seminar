package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private   final int HUMAN_DOT =1;
    private   final int AT_DOT =2;
    private   final int EMPTY_DOT =0;
    private   int fieldSizeY =3;
    private   int fieldSizeX =3;
    private char [][] field;

    private  int panelWidth;
    private  int panelHeight;
    private  int cellWidth;
    private  int cellHeight;

    private void initMap(){
        fieldSizeY =3;
        fieldSizeX =3;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j <fieldSizeX ; j++) {

                field[i][j]=EMPTY_DOT;
            }
        }
    }
//    private void aiTurn(){
//        int x,y;
//        do{
//            x = RANDOM.nextInt(fieldSizeX);
//            y = RANDOM.nextInt(fieldSizeY);
//        }while (!isEmptyCell(x,y));
//        field[y][x] = AI_DOT;
//    }
    Map(){
       addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e){
               update(e);
           }
       });

    }
    private void update(MouseEvent e){
        int cellX = e.getX()/cellWidth;
        int cellY = e.getY()/cellHeight;
        System.out.printf("x=%d, y=%d\n",cellX,cellY);

        repaint();
    }
    void startNewGame ( int mode,int fSzX,int fSzY, int wLen){
        initMap();
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d",mode,fSzX,fSzY,wLen);
        repaint();
    }

      @Override
       protected void paintComponent (Graphics g){
          super.paintComponent(g);
          render(g);
        }

    private void render(Graphics g) {
        panelWidth  = getWidth();
        panelHeight = getHeight();
        cellWidth   = panelHeight/3;
        cellHeight  = panelWidth /3;

        g.setColor(Color.BLACK);
        for (int h = 0; h < 3; h++) {
            int y = h * cellHeight;
            g.drawLine(0,y,panelWidth,y);
        }
        for (int w = 0; w < 3; w++) {
            int x = w * cellWidth;
            g.drawLine(x,0,x,panelHeight);
        }
    }

}
