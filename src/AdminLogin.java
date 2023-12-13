import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLogin {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CMS : Admin Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createTitledBorder("Email Address"));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBackground(Color.WHITE);

        JButton connectButton = createStyledButton("Connect");
        connectButton.setBackground(Color.WHITE);
        JLabel signUpText = new JLabel("Don't have an account?");
        JButton signUpButton = createStyledButton("Sign Up");
        JButton previousPageButton = createStyledButton("Previous Page");

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index.main(new String[] {});
                frame.dispose();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminSignUp.main(new String[] {});
                frame.dispose();
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.length() == 0 || password.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
                    return;
                }

                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(frame, "Email is not valid.");
                    return;
                }

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                            "root", "");
                    PreparedStatement statement = connection
                            .prepareStatement("SELECT * FROM admin WHERE Email_Address = ? AND Password = ?");
                    statement.setString(1, email);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(frame, "Login successful.");
                        AdminDashboard.main(new String[] {});
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed.");
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        panel.add(emailField);
        panel.add(passwordField);
        panel.add(connectButton);
        panel.add(signUpText);
        panel.add(signUpButton);
        panel.add(previousPageButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 50));
        return button;
    }
}
