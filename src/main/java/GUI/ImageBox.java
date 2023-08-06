package GUI;

import javax.swing.*;
import java.awt.*;

public class ImageBox extends JPanel {

    JLabel text;

    ImageBox(String IconPath, String Text, Color color) {
        super();

        setLayout(new GridLayout(2, 1));
        setBackground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon(IconPath);
        JLabel icon = new JLabel(imageIcon);
        icon.setHorizontalAlignment(JLabel.CENTER);
        add(icon);

        JLabel text = new JLabel(Text);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setFont(new Font("arial", Font.BOLD, 15));
        text.setForeground(color);

        add(text);

    }

    ImageBox(String IconPath) {
        super();

        setLayout(new GridLayout(2, 1));
        setBackground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon(IconPath);
        JLabel icon = new JLabel(imageIcon);
        icon.setHorizontalAlignment(JLabel.CENTER);
        add(icon);

    }

    public ImageBox(String Text, Color color) {
        super();

        setLayout(new GridLayout(2, 1));
        setBackground(Color.WHITE);

        text = new JLabel(Text);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setFont(new Font("arial", Font.BOLD, 15));
        text.setForeground(color);

        add(text);
    }

    public void set_text(String text){
        this.text.setText(text);
    }

}
