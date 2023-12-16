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

        JButton addUserButton = createStyledButton("Add User");
        JButton showUserInfo = createStyledButton("Show User Info");
        JButton showClub = createStyledButton("Show Clubs Members");
        JButton showSpeciality = createStyledButton("Show Speciality Members");
        JButton changeUserPws = createStyledButton("Change User Password");
        JButton deleteUser = createStyledButton("Delete User");
        JButton logOutButton = createStyledButton("Log Out");

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

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
}
