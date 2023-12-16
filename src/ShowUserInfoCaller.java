import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ShowUserInfoCaller {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin : Show User Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField cinField = new JTextField(20);
        cinField.setBorder(BorderFactory.createTitledBorder("User CIN"));

        JButton showButton = createStyledButton("Show User Info");
        JButton backButton = createStyledButton("Back");

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = cinField.getText();
                if (cin == null || cin.trim().equals("") || cin.length() != 8 || !cin.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid CIN");
                    return;

                }

                try {
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/club_management", "root", "");
                    PreparedStatement ps = connection.prepareStatement(
                            "SELECT Mail_Address FROM user WHERE CIN = ?");
                    ps.setString(1, cin);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String mailAddress = rs.getString("Mail_Address");
                        ShowUserInfo.main(new String[] { mailAddress });
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "User not found");
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminDashboard.main(new String[] {});
                frame.dispose();
            }
        });

        panel.add(cinField);
        panel.add(showButton);
        panel.add(backButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

}