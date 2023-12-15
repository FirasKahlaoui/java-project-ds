import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShowClubs {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("Show Clubs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/club_management",
                    "root", "");

            PreparedStatement ps = connection
                    .prepareStatement("SELECT CIN FROM user WHERE Mail_Address = ?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String cin = rs.getString("CIN");

                ps = connection.prepareStatement(
                        "SELECT NumClub FROM participate WHERE CIN = ?");
                ps.setString(1, cin);
                rs = ps.executeQuery();

                boolean hasClubs = false;

                while (rs.next()) {
                    hasClubs = true;
                    String numClub = rs.getString("NumClub");

                    ps = connection.prepareStatement(
                            "SELECT NameClub, Type FROM club WHERE NumClub = ?");
                    ps.setString(1, numClub);
                    ResultSet rsClub = ps.executeQuery();

                    if (rsClub.next()) {
                        String clubInfo = "Name: " + rsClub.getString("NameClub")
                                + " Type: " + rsClub.getString("Type");
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

            JButton doneButton = createStyledButton("Done");
            doneButton.addActionListener(e -> {
                UserDash.main(new String[] { userEmail });
                frame.dispose();
            });
            frame.add(panel, BorderLayout.CENTER);
            frame.add(doneButton, BorderLayout.PAGE_END);
            frame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 40));
        return button;
    }
}