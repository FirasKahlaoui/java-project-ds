import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangeUserPws {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin : Change User Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 280);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField cinField = new JTextField(20);
        cinField.setBorder(BorderFactory.createTitledBorder("User CIN"));
        JTextField passwordField = new JTextField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("New Password"));

        JButton changeButton = ButtonUtils.createStyledButton("Change Password", 12);
        JButton backPage = ButtonUtils.createStyledButton("Back", 12);

        backPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminDashboard.main(new String[] {});
                frame.dispose();
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cin = cinField.getText();
                String password = passwordField.getText();

                if (cin == null || cin.trim().equals("")) {
                    JOptionPane.showMessageDialog(frame, "CIN can't be blank");
                } else if (password == null || password.trim().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Password can't be blank");
                } else if (password.length() < 6) {
                    JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long");

                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/club_management", "root", "");
                        PreparedStatement ps = con.prepareStatement(
                                "SELECT * FROM user WHERE CIN=?");
                        ps.setString(1, cin);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            ps = con.prepareStatement(
                                    "update user set Password=? where CIN=?");
                            ps.setString(1, password);
                            ps.setString(2, cin);
                            int i = ps.executeUpdate();
                            if (i > 0) {
                                JOptionPane.showMessageDialog(frame, "Password changed successfully");
                                AdminDashboard.main(new String[] {});
                                frame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(frame, "Error while changing password");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "User does not exist");
                        }
                        con.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel.add(cinField);
        panel.add(passwordField);
        panel.add(changeButton);
        panel.add(backPage);

        frame.add(panel);
        frame.setVisible(true);

    }

}