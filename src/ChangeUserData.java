import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangeUserData {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("Change User Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField cinField = new JTextField(20);
        cinField.setBorder(BorderFactory.createTitledBorder("CIN :"));
        JTextField nameField = new JTextField(20);
        nameField.setBorder(BorderFactory.createTitledBorder("Name :"));
        JTextField lastNameField = new JTextField(20);
        lastNameField.setBorder(BorderFactory.createTitledBorder("Last Name :"));
        JTextField ageField = new JTextField(20);
        ageField.setBorder(BorderFactory.createTitledBorder("Age :"));

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
        specialityComboBox.setBorder(BorderFactory.createTitledBorder("Speciality :"));

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                    "root", "");

            PreparedStatement ps = connection
                    .prepareStatement("SELECT CIN, Name, LastName, Age, Speciality FROM user WHERE Mail_Address = ?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cinField.setText(rs.getString("CIN"));
                nameField.setText(rs.getString("Name"));
                lastNameField.setText(rs.getString("LastName"));
                ageField.setText(String.valueOf(rs.getInt("Age")));
                specialityComboBox.setSelectedItem(rs.getString("Speciality"));
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDash.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        JButton confirmButton = createStyledButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = cinField.getText();
                String name = nameField.getText();
                String lastName = lastNameField.getText();
                String age = ageField.getText();
                String speciality = (String) specialityComboBox.getSelectedItem();
                if (cin.length() == 0 || name.length() == 0 || lastName.length() == 0 || age.length() == 0
                        || speciality.equals("-- Select a speciality --")) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
                    return;
                }
                if (!cin.matches("[0-9]+") || cin.length() != 8 || (cin.charAt(0) != '0' && cin.charAt(0) != '1')) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid CIN.");
                    return;
                }
                if (name.length() < 3 || !name.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid name.");
                    return;

                }
                if (lastName.length() < 3 || !lastName.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid last name.");
                    return;

                }
                if (age.length() != 2 || Integer.parseInt(age) < 18) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age.");
                    return;
                }
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                            "root", "");

                    PreparedStatement ps = connection
                            .prepareStatement(
                                    "UPDATE user SET CIN = ?, Name = ?, LastName = ?, Age = ?, Speciality = ? WHERE Mail_Address = ?");
                    ps.setString(1, cin);
                    ps.setString(2, name);
                    ps.setString(3, lastName);
                    ps.setInt(4, Integer.parseInt(age));
                    ps.setString(5, speciality);
                    ps.setString(6, userEmail);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "User data updated successfully.");
                    UserDash.main(new String[] { userEmail });
                    frame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "An error occurred while updating the user data: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);
        panel.add(cinField);
        panel.add(nameField);
        panel.add(lastNameField);
        panel.add(ageField);
        panel.add(specialityComboBox);
        panel.add(buttonPanel);

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