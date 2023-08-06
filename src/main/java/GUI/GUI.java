package GUI;

import Client.Board;
import Tools.Global;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public Board board;

    public Header header;
    public GUI() {

        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("myicon.png"));
        setIconImage(img.getImage());
        Taskbar.getTaskbar().setIconImage(img.getImage());

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        header = new Header();

        board = new Board(this , header);

        jPanel.add(header, BorderLayout.NORTH);
        jPanel.add(board, BorderLayout.CENTER);

        getContentPane().add(jPanel);

        setBounds(0, 0, Global.width, Global.height);
        setResizable(false);
        setTitle("Snake");

//        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
