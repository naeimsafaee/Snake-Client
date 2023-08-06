package GUI;

import Client.Board;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {

    ImageBox imageBox , imageBox1;

    Header() {
        super();
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        imageBox = new ImageBox("", Color.BLACK);

        imageBox1 = new ImageBox("", Color.BLACK);

        add(imageBox);
        add(imageBox1);

        setBackground(Color.WHITE);
//            add(headerText);

    }

    public void setSize(int size){
        imageBox.set_text("Size: " + size);
    }

    public void setOnlineSnakes(int totalNumber) {
        imageBox1.set_text("Online users: " + totalNumber);
    }

}
