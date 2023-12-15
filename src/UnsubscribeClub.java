import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UnsubscribeClub {
    public static void main(String[] args) {
        String userEmail = args[0];
        JFrame frame = new JFrame("Unsubscribe Club");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> clubComboBox = new JComboBox<>();
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

        JButton unsubscribeButton = createStyledButton("Unsubscribe");
        unsubscribeButton.addActionListener(e -> {
            // Unsubscribe logic here
        });

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            // Back logic here
        });

        panel.add(unsubscribeButton);
        panel.add(backButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 60));
        return button;
    }
}