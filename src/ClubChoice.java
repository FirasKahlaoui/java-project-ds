import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClubChoice
 */
public class ClubChoice {

    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("User : Club Subscription ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 240);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel title = new JLabel("Choose a club to subscribe to:");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.BLACK);
        title.setBackground(Color.WHITE);
        panel.add(title);

        String[] clubs = {
                "-- Select a club --",
                "Coding Wizards",
                "Green Earth Society",
                "Musical Harmony",
                "Fitness Fanatics",
                "Bookworms Club",
                "Innovation Hub",
                "Dance Fusion",
                "Sports Unlimited",
                "Nature Explorers",
                "Film Buffs Society"
        };
        JComboBox<String> clubComboBox = new JComboBox<>(clubs);
        panel.add(clubComboBox);

        JButton Back = ButtonUtils.createStyledButton("Back", 16);
        JButton SubscribeButton = ButtonUtils.createStyledButton("Subscribe", 16);
        panel.add(SubscribeButton);
        panel.add(Back);

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDash.main(new String[] { userEmail });
                frame.dispose();
            }
        });

        SubscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String club = (String) clubComboBox.getSelectedItem();
                int optionIndex = clubComboBox.getSelectedIndex();

                if (club.equals("-- Select a club --")) {
                    JOptionPane.showMessageDialog(frame, "Please select a club.");
                    return;
                }

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                            "root", "");

                    PreparedStatement ps = connection.prepareStatement("SELECT CIN FROM user WHERE Mail_Address = ?");
                    ps.setString(1, userEmail);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String CIN = rs.getString("CIN");

                        ps = connection.prepareStatement("SELECT * FROM participate WHERE CIN = ? AND NumClub = ?");
                        ps.setString(1, CIN);
                        ps.setInt(2, optionIndex);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(frame, "You are already subscribed to " + club + ".");
                        } else {
                            ps = connection
                                    .prepareStatement("INSERT INTO participate (NumClub,CIN, Date) VALUES (?, ?, ?)");
                            ps.setInt(1, optionIndex);
                            ps.setString(2, CIN);
                            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(frame, "You have subscribed to " + club + ".");
                            UserDash.main(new String[] { userEmail });
                            frame.dispose();
                        }
                    }

                    connection.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        frame.add(panel);
        frame.setVisible(true);
    }

}