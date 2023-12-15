import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddUser {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin : Add User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField CinField = new JTextField(20);
        CinField.setBorder(BorderFactory.createTitledBorder("CIN"));
        JTextField firstNameField = new JTextField(20);
        firstNameField.setBorder(BorderFactory.createTitledBorder("First Name"));

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBorder(BorderFactory.createTitledBorder("Last Name"));

        JTextField emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createTitledBorder("Email Address"));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JTextField ageField = new JTextField(20);
        ageField.setBorder(BorderFactory.createTitledBorder("Age"));

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

        JButton addButton = createStyledButton("Add User");
        JButton backPage = createStyledButton("Back");

        backPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AdminDashboard.main(new String[] {});
                frame.dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = CinField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String age = ageField.getText();
                String speciality = (String) specialityComboBox.getSelectedItem();

                if (cin.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || email.length() == 0
                        || password.length() == 0 || age.length() == 0 || speciality.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
                    return;
                }

                if (!cin.matches("[0-9]+") || cin.length() != 8 || (cin.charAt(0) != '0' && cin.charAt(0) != '1')) {
                    JOptionPane.showMessageDialog(frame, "CIN must be 8 characters long and start with 0 or 1.");
                    return;
                }
                if (firstName.length() < 3 || !firstName.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(frame,
                            "First name must be at least 3 characters long and contain only letters.");
                    return;
                }
                if (lastName.length() < 3 || !lastName.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(frame,
                            "Last name must be at least 3 characters long and contain only letters.");
                    return;
                }

                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(frame, "Email is not valid.");
                    return;
                }

                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(frame, "Password must be at least 6 characters long.");
                    return;
                }

                if (!age.matches("[0-9]+") || age.length() > 2 || Integer.parseInt(age) < 18
                        || Integer.parseInt(age) > 99) {
                    JOptionPane.showMessageDialog(frame, "Age must be at least 18");
                    return;
                }

                Connection conn = null;
                PreparedStatement stmt = null;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management", "root", "");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error connecting to the database.");
                        return;
                    }
                    String sql = "SELECT * FROM user WHERE CIN = ? OR Mail_Address = ?";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, cin);
                        stmt.setString(2, email);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(frame,
                                    "CIN or email already exist in the database. Please try again.");
                            return;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,
                                "Error checking if CIN or email already exist in the database.");
                        return;
                    }
                    sql = "INSERT INTO user (CIN, Name, LastName, Mail_Address, Password,Age,Speciality) VALUES (?,?,?,?,?,?,?)";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, cin);
                        stmt.setString(2, firstName);
                        stmt.setString(3, lastName);
                        stmt.setString(4, email);
                        stmt.setString(5, password);
                        stmt.setString(6, age);
                        stmt.setString(7, speciality);
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error inserting data into the database.");
                        return;
                    }

                    JOptionPane.showMessageDialog(frame, "User added successfully.");
                    AdminDashboard.main(new String[] {});
                    frame.dispose();

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error loading the JDBC driver.");
                } finally {

                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        panel.add(CinField);
        panel.add(firstNameField);
        panel.add(lastNameField);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(ageField);
        panel.add(specialityComboBox);
        panel.add(addButton);
        panel.add(backPage);
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