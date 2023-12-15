import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePws {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("User : Change Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 280);
        frame.getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPasswordField oldPasswordField = new JPasswordField(20);
        oldPasswordField.setBorder(BorderFactory.createTitledBorder("Old Password"));
        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JPasswordField confirmNewPasswordField = new JPasswordField(20);
        confirmNewPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        JButton changePasswordButton = createStyledButton("Change Password");
        JButton backButton = createStyledButton("Back");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(changePasswordButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDash.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmNewPassword = new String(confirmNewPasswordField.getPassword());
                if (oldPassword.length() == 0 || newPassword.length() == 0 || confirmNewPassword.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the fields.");
                    return;

                }

                if (newPassword.length() < 6) {
                    JOptionPane.showMessageDialog(frame, "Password should be at least 6 characters long.");
                    return;
                }
                if (newPassword.equals(oldPassword)) {
                    JOptionPane.showMessageDialog(frame, "New password should be different from old password.");
                    return;

                }
                if (!newPassword.equals(confirmNewPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password and Confirm Password must be the same.");
                    return;
                }

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                            "root", "");

                    PreparedStatement ps = connection
                            .prepareStatement("SELECT Password FROM user WHERE Mail_Address = ?");
                    ps.setString(1, userEmail);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String currentPassword = rs.getString("Password");

                        if (currentPassword.equals(oldPassword)) {
                            if (newPassword.equals(confirmNewPassword)) {
                                PreparedStatement psUpdate = connection
                                        .prepareStatement("UPDATE user SET Password = ? WHERE Mail_Address = ?");
                                psUpdate.setString(1, newPassword);
                                psUpdate.setString(2, userEmail);
                                psUpdate.executeUpdate();

                                JOptionPane.showMessageDialog(frame,
                                        "Password changed successfully. You have been logged out.");
                                UserLogin.main(new String[] {});
                                frame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(frame, "New passwords do not match.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Old password is incorrect.");
                        }
                    }

                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(oldPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(newPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(confirmNewPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
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