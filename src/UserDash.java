import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserDash {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("CMS : User Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JButton showData = ButtonUtils.createStyledButton("User Info", 12);
        JButton showClub = ButtonUtils.createStyledButton("Show Clubs", 12);
        JButton clubSubscribe = ButtonUtils.createStyledButton("Subscribe to a club", 12);
        JButton clubUnsubscribe = ButtonUtils.createStyledButton("Unsubscribe from a club", 12);
        JButton changeData = ButtonUtils.createStyledButton("Change User Data", 12);
        JButton changePassword = ButtonUtils.createStyledButton("Change Password", 12);
        JButton deleteAccount = ButtonUtils.createStyledButton("Delete Account", 12);
        JButton logout = ButtonUtils.createStyledButton("Logout", 12);

        showData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowUserData.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        showClub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowClubs.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        clubSubscribe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClubChoice.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        clubUnsubscribe.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UnsubscribeClub.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        changeData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeUserData.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePws.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Do you want to delete your account?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/club_management",
                                "root", "");

                        PreparedStatement ps = connection
                                .prepareStatement("DELETE FROM user WHERE Mail_Address = ?");
                        ps.setString(1, userEmail);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Your account has been deleted.");
                        UserLogin.main(new String[] { userEmail });
                        frame.dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have been logged out.");
                UserLogin.main(new String[] {});
                frame.dispose();
            }
        });

        panel.add(showData);
        panel.add(showClub);
        panel.add(clubSubscribe);
        panel.add(clubUnsubscribe);
        panel.add(changeData);
        panel.add(changePassword);
        panel.add(deleteAccount);
        panel.add(logout);

        frame.add(panel);
        frame.setVisible(true);
    }

}