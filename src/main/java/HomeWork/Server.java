package HomeWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Server extends JFrame {
    private static final int WINDOW_POSX = 200;
    private static final int WINDOW_POSY = 250;
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 100;
    JButton btnStart = new JButton("Start Server");
    JButton btnExit = new JButton("Stop Server");
    boolean isServicesWorking;

        public Server(Chat chat){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(chat);
        setTitle("Server");
        setResizable(false);
        setLayout(new GridLayout(1,2));
        add(btnStart);
        add(btnExit);
        setVisible(true);
        btnStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!isServicesWorking){
                    isServicesWorking = true;
                    System.out.println("ServerIsWorking: "+ true);
                }
            }
        });
        btnExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(isServicesWorking){
                    isServicesWorking = false;
                    System.out.println("ServerIsWorking: "+ false);
                }
            }
        });
    }

}

