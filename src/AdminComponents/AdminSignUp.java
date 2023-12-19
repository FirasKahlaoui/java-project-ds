package AdminComponents;

import Button.*;
import DataBase.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminSignUp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CMS : Admin Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField firstNameField = new JTextField(20);
        firstNameField.setBorder(BorderFactory.createTitledBorder("First Name"));
        JTextField lastNameField = new JTextField(20);
        lastNameField.setBorder(BorderFactory.createTitledBorder("Last Name"));
        JTextField emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createTitledBorder("Email Address"));
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        JButton signUpButton = ButtonUtils.createStyledButton("Sign Up", 12);
        JLabel text = new JLabel("Already have an account?");
        JButton LoginPageButton = ButtonUtils.createStyledButton("Log In", 12);
        LoginPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin.main(new String[] {});
                frame.dispose();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (firstName.length() == 0 || lastName.length() == 0 || email.length() == 0
                        || password.length() == 0 || confirmPassword.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
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

                if (firstName.length() < 3 || lastName.length() < 3) {
                    JOptionPane.showMessageDialog(frame,
                            "First Name and Last Name must be at least 3 characters long.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password and Confirm Password must be the same.");
                    return;
                }
                Connection conn = null;
                PreparedStatement stmt = null;

                try {

                    try {
                        conn = DatabaseConnection.getConnection();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error connecting to the database.");
                        return;
                    }

                    String sql = "SELECT * FROM admin WHERE Email_Address =?";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, email);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(frame,
                                    "Email already exist in the database.Try to Log In.");
                            return;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,
                                "Error checking if Email already exist in the database.");
                        return;
                    }

                    sql = "INSERT INTO admin (FirstName, LastName, Email_Address, Password) VALUES (?,?,?,?)";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, firstName);
                        stmt.setString(2, lastName);
                        stmt.setString(3, email);
                        stmt.setString(4, password);

                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error inserting data into the database.");
                        return;
                    }

                    JOptionPane.showMessageDialog(frame, "User signed up successfully.");
                    AdminLogin.main(new String[] {});
                    frame.dispose();
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
        panel.add(firstNameField);
        panel.add(lastNameField);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(passwordField);
        panel.add(confirmPasswordField);
        panel.add(signUpButton);
        panel.add(text);
        panel.add(LoginPageButton);
        frame.add(panel);
        frame.setVisible(true);

    }

}