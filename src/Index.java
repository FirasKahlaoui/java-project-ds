import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Club Managing System ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Welcome to the Club Managing System");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.BLACK);
        title.setBackground(Color.WHITE);

        JLabel authors = new JLabel("By : Firas Kahlaoui & Yasmine Zaatour  : 2 BD 1");
        authors.setFont(new Font("Arial", Font.BOLD, 14));
        authors.setAlignmentX(Component.CENTER_ALIGNMENT);
        authors.setForeground(Color.BLACK);
        authors.setBackground(Color.WHITE);

        JButton adminButton = ButtonUtils.createStyledButton("Admin", 16);

        JButton userButton = ButtonUtils.createStyledButton("User", 16);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserLogin.main(new String[] {});
                frame.dispose();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin.main(new String[] {});
                frame.dispose();
            }
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(adminButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(userButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(authors);

        frame.add(panel);
        frame.setVisible(true);
    }
}
