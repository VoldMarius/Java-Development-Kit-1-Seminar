package HomeWork;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import java.util.Date;


public class Chat extends JFrame {
    private static final int WINDOW_POSX = 100;
    private static final int WINDOW_POSY = 100;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    private FileMassage fileRider = new FileMassage();
    JTextField loginField;
    JTextField passwordField;
    JTextField messageField;
    JLabel lbLogin;

    JLabel lbPassword;

    JTextArea messagesArea;
    JButton sendMessageButton;
    public Chat() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat");
        setResizable(true);
        JPanel serverPanel = new JPanel(new GridLayout(4,1));
        lbLogin = new JLabel("Login");
        serverPanel.add(lbLogin);
        loginField = new JTextField();
        serverPanel.add(loginField);
        lbPassword = new JLabel("Password");
        serverPanel.add(lbPassword);
        passwordField = new JTextField();
        serverPanel.add(passwordField);
        setLayout(new GridLayout(2,1));
        add(serverPanel);
        JPanel clientPanel = new JPanel(new GridLayout(5,1));
        JLabel lbChatArea = new JLabel("Chat");
        clientPanel.add(lbChatArea);
        messagesArea = new JTextArea();
        clientPanel.add(messagesArea);
        JLabel lbNewMessage = new JLabel("Your message:");
        clientPanel.add(lbNewMessage);
        messageField = new JTextField();
        clientPanel.add(messageField);
        sendMessageButton = new JButton("Send");
        clientPanel.add(sendMessageButton);
        add(clientPanel);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {{if (server.isServicesWorking)
                sendMessage();
            }
            }
        });
        messageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {if (server.isServicesWorking){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        try{if (server.isServicesWorking){
            for (String mess:fileRider.load("chatLog.txt")) {
                messagesArea.append(MessageSender(mess));
            }
        }
        }catch(IOException e){}

        setVisible(true);
    }
    Server server = new Server(this);
    private void sendMessage(){
        String sendingMessage = loginField.getText() + ": " + messageField.getText() + "\n";
        messagesArea.append(sendingMessage);
        String logMessage = String.format("Date/time: %s, User: %s, message: %s\n", new Date(), loginField.getText(), messageField.getText());
        messageField.setText("");
        System.out.printf(logMessage);
        fileRider.save(logMessage, "chatLog.txt");
    }

    private String  MessageSender(String loadingLine){
        int userStart = loadingLine.indexOf("User: ");
        int messageIndex = loadingLine.indexOf(", message: ");
        String rezult = loadingLine.substring(userStart+6, messageIndex) + loadingLine.substring(messageIndex+9)+"\n";
        return rezult;
    }
}

