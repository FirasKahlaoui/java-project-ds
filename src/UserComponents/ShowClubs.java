package UserComponents;

import Button.*;
import DataBase.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShowClubs {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("User : Show Clubs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement ps = connection
                    .prepareStatement("SELECT CIN FROM user WHERE Mail_Address = ?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String cin = rs.getString("CIN");

                ps = connection.prepareStatement(
                        "SELECT NumClub, Date FROM participate WHERE CIN = ?");
                ps.setString(1, cin);
                rs = ps.executeQuery();

                boolean hasClubs = false;

                while (rs.next()) {
                    hasClubs = true;
                    String numClub = rs.getString("NumClub");
                    Date date = rs.getDate("Date");

                    ps = connection.prepareStatement(
                            "SELECT NameClub, Type FROM club WHERE NumClub = ?");
                    ps.setString(1, numClub);
                    ResultSet rsClub = ps.executeQuery();

                    if (rsClub.next()) {
                        String clubInfo = "<html><h2>" + rsClub.getString("NameClub") + "</h2>"
                                + "<p><b>Type:</b> " + rsClub.getString("Type") + "</p>"
                                + "<p><b>Date:</b> " + date + "</p></html>";
                        JLabel clubLabel = new JLabel(clubInfo);
                        clubLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        panel.add(clubLabel);
                    }
                }

                if (!hasClubs) {
                    JLabel messageLabel = new JLabel("You are not subscribed to any clubs.");
                    messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    panel.add(messageLabel);
                }
            }

            JButton doneButton = ButtonUtils.createStyledButton("Done", 12);
            doneButton.addActionListener(e -> {
                UserDash.main(new String[] { userEmail });
                frame.dispose();
            });
            JScrollPane scrollPane = new JScrollPane(panel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(doneButton, BorderLayout.PAGE_END);
            frame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}