package Button;

import javax.swing.*;
import java.awt.*;

public class ButtonUtils {
    public static JButton createStyledButton(String text, int size) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode("#2babff"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, size));
        button.setPreferredSize(new Dimension(100, 50));
        return button;
    }
}