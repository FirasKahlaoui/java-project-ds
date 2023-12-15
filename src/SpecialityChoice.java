import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SpecialityChoice {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/club_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("CMS - Speciality Choice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 240);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Age:"));
        JTextField ageTextField = new JTextField();
        panel.add(ageTextField);
        panel.add(new JLabel("Speciality:"));
        String[] specialities = {
                "-- Select a speciality --",
                "Big Data Analytics",
                "Full Stack Development",
                "Machine Learning and Artificial Intelligence",
                "Cybersecurity",
                "Cloud Computing",
                "Blockchain Technology"
        };
        JComboBox<String> specialityComboBox = new JComboBox<>(specialities);
        panel.add(specialityComboBox);

        JButton LogOutButton = createStyledButton("Log Out");
        JButton NextButton = createStyledButton("Next");
        panel.add(LogOutButton);
        panel.add(NextButton);

        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have been logged out.");
                UserLogin.main(new String[] {});
                frame.dispose();
            }
        });

        NextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String age = ageTextField.getText();
                String speciality = (String) specialityComboBox.getSelectedItem();

                if (age.length() == 0 || speciality.equals("-- Select a speciality --")) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
                    return;
                }

                try {
                    int ageValue = Integer.parseInt(age);

                    if (ageValue < 18) {
                        JOptionPane.showMessageDialog(frame, "You must be at least 18 years old to proceed.");
                        return;
                    }

                    updateUserData(userEmail, ageValue, speciality);
                    ClubChoice.main(new String[] { userEmail });

                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void updateUserData(String userEmail, int age, String speciality) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String updateSql = "UPDATE user SET Age = ?, Speciality = ? WHERE Mail_Address = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, age);
                updateStmt.setString(2, speciality);
                updateStmt.setString(3, userEmail);

                updateStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user data.");
        }
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(100, 50));
        return button;
    }
}
