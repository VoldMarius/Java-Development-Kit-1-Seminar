package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;
    JButton btnStart = new JButton("Start New Game");
    JLabel gameMode = new JLabel("Выберите режим игры");
    JLabel selectFieldSize = new JLabel("Выберите размерность для игрового поля");
    JLabel  dimensionsPlayingField = new JLabel("Размер изменяется в пределах   от 3   до 9");
    JLabel lengthСombinationToWin = new JLabel("Задайте длину комбинации для победы");
    JSlider sizeField = new JSlider(3,9);

    JSlider lengthToWin = new JSlider(3,9);
    JRadioButton humanVsAi = new JRadioButton("Человек против компьютера");
    JRadioButton humanVsHuman = new JRadioButton("Человек против Человека");
    JPanel panBottom;
    ButtonGroup gameOptionButtons = new ButtonGroup();

    SettingsWindow(GameWindow gameWindow){
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        panBottom = new JPanel(new GridLayout(10,11));


        panBottom.add(gameMode);
        gameOptionButtons.add(humanVsAi);
        gameOptionButtons.add(humanVsHuman);
        panBottom.add(humanVsAi);
        panBottom.add(humanVsHuman);
        panBottom.add(selectFieldSize);
        panBottom.add(dimensionsPlayingField);
        panBottom.add(sizeField);
        panBottom.add(lengthСombinationToWin);
        panBottom.add(lengthToWin);

        panBottom.add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0,3,3,3);
                        setVisible(false);
            }
        });
        add(panBottom);


    }
}
