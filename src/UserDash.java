import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDash {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("User Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JButton showData = createStyledButton("User Info");
        JButton showClub = createStyledButton("Show Clubs");
        JButton clubSubscribe = createStyledButton("Subscribe to a Club");
        JButton clubUnsubscribe = createStyledButton("Unsubscribe from a Club");
        JButton changeData = createStyledButton("Change User Data");
        JButton changePassword = createStyledButton("Change Password");
        JButton deleteAccount = createStyledButton("Delete Account");
        JButton logout = createStyledButton("Log Out");

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

        /*
         * deleteAccount.addActionListener(new ActionListener() {
         * 
         * @Override
         * public void actionPerformed(ActionEvent e) {
         * DeleteAccount.main(new String[] { userEmail });
         * frame.dispose();
         * }
         * });
         */

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

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
}