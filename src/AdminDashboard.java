import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CMS : Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addUserButton = ButtonUtils.createStyledButton("Add User", 12);
        JButton showUserInfo = ButtonUtils.createStyledButton("Show User Info", 12);
        JButton showClub = ButtonUtils.createStyledButton("Show Clubs Members", 12);
        JButton showSpeciality = ButtonUtils.createStyledButton("Show Speciality Members", 12);
        JButton changeUserPws = ButtonUtils.createStyledButton("Change User Password", 12);
        JButton deleteUser = ButtonUtils.createStyledButton("Delete User", 12);
        JButton logOutButton = ButtonUtils.createStyledButton("Log Out", 12);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUser.main(new String[] {});
                frame.dispose();
            }
        });

        showUserInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowUserInfoCaller.main(new String[] {});
                frame.dispose();
            }
        });

        showClub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowMemberClub.main(new String[] {});
                frame.dispose();
            }
        });

        showSpeciality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowMemberSpeciality.main(new String[] {});
                frame.dispose();
            }
        });

        changeUserPws.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeUserPws.main(new String[] {});
                frame.dispose();
            }
        });

        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteUser.main(new String[] {});
                frame.dispose();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have been logged out");
                AdminLogin.main(new String[] {});
                frame.dispose();
            }
        });

        panel.add(addUserButton);
        panel.add(showUserInfo);
        panel.add(showClub);
        panel.add(showSpeciality);
        panel.add(changeUserPws);
        panel.add(deleteUser);
        panel.add(logOutButton);

        frame.add(panel);
        frame.setVisible(true);
    }

}
