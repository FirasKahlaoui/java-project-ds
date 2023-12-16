import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UnsubscribeClub {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("User : Club Unsubscription");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> clubComboBox = new JComboBox<>();
        clubComboBox.setPreferredSize(new Dimension(400, 70));
        clubComboBox.setMaximumSize(new Dimension(400, 70));
        clubComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        clubComboBox.setBackground(Color.WHITE);

        panel.add(clubComboBox);

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

                while (rs.next()) {
                    String numClub = rs.getString("NumClub");

                    ps = connection.prepareStatement(
                            "SELECT NameClub FROM club WHERE NumClub = ?");
                    ps.setString(1, numClub);
                    ResultSet rsClub = ps.executeQuery();

                    if (rsClub.next()) {
                        String clubName = rsClub.getString("NameClub");
                        clubComboBox.addItem(clubName);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JButton unsubscribeButton = ButtonUtils.createStyledButton("Unsubscribe", 12);
        unsubscribeButton.addActionListener(e -> {
            String selectedClub = (String) clubComboBox.getSelectedItem();
            if (selectedClub != null) {
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
                                "SELECT NumClub FROM club WHERE NameClub = ?");
                        ps.setString(1, selectedClub);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            String numClub = rs.getString("NumClub");

                            ps = connection.prepareStatement(
                                    "DELETE FROM participate WHERE CIN = ? AND NumClub = ?");
                            ps.setString(1, cin);
                            ps.setString(2, numClub);
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "You have unsubscribed from " + selectedClub);
                            UserDash.main(new String[] { userEmail });
                            frame.dispose();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton backButton = ButtonUtils.createStyledButton("Back", 12);
        backButton.addActionListener(e -> {
            UserDash.main(new String[] { userEmail });
            frame.dispose();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(backButton);
        buttonPanel.add(unsubscribeButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}