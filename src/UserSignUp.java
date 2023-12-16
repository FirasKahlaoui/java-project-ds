import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSignUp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CMS : User Login");
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

        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));

        JLabel loginText = new JLabel("Already have an account?");

        JButton signUpButton = ButtonUtils.createStyledButton("Sign Up",12);
        JButton LoginPageButton = ButtonUtils.createStyledButton("Login Page",12);

        LoginPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserLogin.main(new String[] {});
                frame.dispose();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = CinField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (cin.length() == 0 || firstName.length() == 0 || lastName.length() == 0 || email.length() == 0
                        || password.length() == 0 || confirmPassword.length() == 0) {
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

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password and Confirm Password must be the same.");
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
                    String sql = "SELECT * FROM user WHERE CIN =? OR Mail_Address =?";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, cin);
                        stmt.setString(2, email);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(frame,
                                    "CIN or email already exist in the database.Try to Log In.");
                            return;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,
                                "Error checking if CIN or email already exist in the database.");
                        return;
                    }

                    sql = "INSERT INTO user (CIN, Name, LastName, Mail_Address, Password) VALUES (?,?,?,?,?)";
                    try {
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, cin);
                        stmt.setString(2, firstName);
                        stmt.setString(3, lastName);
                        stmt.setString(4, email);
                        stmt.setString(5, password);
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error inserting data into the database.");
                        return;
                    }

                    JOptionPane.showMessageDialog(frame, "User signed up successfully.");
                    UserLogin.main(new String[] {});
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
        panel.add(confirmPasswordField);
        panel.add(signUpButton);
        panel.add(loginText);
        panel.add(LoginPageButton);

        frame.add(panel);
        frame.setVisible(true);
    }

}
