import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowUserData {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("CMS - User Informations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 280);
        frame.getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        JButton backButton = createStyledButton("Done");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management", "root",
                    "");

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE Mail_Address = ?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String cin = rs.getString("CIN");
                String name = rs.getString("Name");
                String lastName = rs.getString("LastName");
                String mailAddress = rs.getString("Mail_Address");
                String password = rs.getString("Password");
                String age = rs.getString("Age");
                String speciality = rs.getString("Speciality");

                JPasswordField passwordField = new JPasswordField(password);
                passwordField.setEchoChar('*');
                JButton showPasswordButton = createStyledButton("Show Password");
                showPasswordButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (passwordField.getEchoChar() == '*') {
                            passwordField.setEchoChar((char) 0);
                            showPasswordButton.setText("Hide Password");
                        } else {
                            passwordField.setEchoChar('*');
                            showPasswordButton.setText("Show Password");
                        }
                    }
                });

                panel.add(new JLabel("CIN:"));
                panel.add(new JLabel(cin));
                panel.add(new JLabel("Name:"));
                panel.add(new JLabel(name));
                panel.add(new JLabel("Last Name:"));
                panel.add(new JLabel(lastName));
                panel.add(new JLabel("Email:"));
                panel.add(new JLabel(mailAddress));
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);
                panel.add(new JLabel("Age:"));
                panel.add(new JLabel(age));
                panel.add(new JLabel("Speciality:"));
                panel.add(new JLabel(speciality));
                panel.add(showPasswordButton);
                panel.add(backButton);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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