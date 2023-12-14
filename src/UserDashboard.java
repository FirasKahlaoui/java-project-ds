import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboard {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/club_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Club Managing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (args.length == 1) {
            String userEmail = args[0];
            if (loadUserData(userEmail)) {
                // User has age and speciality as NULL, so load specialityChoice
                new UserDashboard().specialityChoice(userEmail);
            } else {
                // User has valid data, so load clubChoice
                new UserDashboard().clubChoice(userEmail);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid arguments.");
        }
    }

    private static boolean loadUserData(String userEmail) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT Age, Speciality FROM user WHERE Mail_Address = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userEmail);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    int age = rs.getInt("Age");
                    String speciality = rs.getString("Speciality");
                    return age == 0 && speciality == null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void specialityChoice(String userEmail) {
        JFrame frame = new JFrame("CMS - Speciality Choice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 240);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Age:"));
        JTextField ageTextField = new JTextField();
        panel.add(ageTextField);
        panel.add(new JLabel("Speciality:"));
        String[] specialities = {
                "-- Select a speciality --",
                "Big Data Analytics",
                "Full Stack Development",
                "Machine Learning and Artificial Intelligence",
                "Cybersecurity",
                "Cloud Computing",
                "Blockchain Technology"
        };
        JComboBox<String> specialityComboBox = new JComboBox<>(specialities);
        panel.add(specialityComboBox);

        JButton LogOutButton = createStyledButton("Log Out");
        JButton NextButton = createStyledButton("Next");
        panel.add(LogOutButton);
        panel.add(NextButton);

        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have been logged out.");
                UserLogin.main(new String[] {});
                frame.dispose();
            }
        });

        NextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String age = ageTextField.getText();
                String speciality = (String) specialityComboBox.getSelectedItem();

                if (age.length() == 0 || speciality.equals("-- Select a speciality --")) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
                    return;
                }

                try {
                    int ageValue = Integer.parseInt(age);

                    if (ageValue < 18) {
                        JOptionPane.showMessageDialog(frame, "You must be at least 18 years old to proceed.");
                        return;
                    }

                    updateUserData(userEmail, ageValue, speciality);

                    clubChoice(userEmail);

                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateUserData(String userEmail, int age, String speciality) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String updateSql = "UPDATE user SET Age = ?, Speciality = ? WHERE Mail_Address = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, age);
                updateStmt.setString(2, speciality);
                updateStmt.setString(3, userEmail);

                updateStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user data.");
        }
    }

    private void clubChoice(String userEmail) {
        JFrame frame = new JFrame("CMS - Club Choice ");
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

        JButton LogOutButton = createStyledButton("Log Out");
        JButton SubscribeButton = createStyledButton("Subscribe");
        panel.add(SubscribeButton);
        panel.add(LogOutButton);

        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have been logged out.");
                UserLogin.main(new String[] {});
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

                        // Check if the user is already subscribed to the club
                        ps = connection.prepareStatement("SELECT * FROM participate WHERE CIN = ? AND NumClub = ?");
                        ps.setString(1, CIN);
                        ps.setInt(2, optionIndex);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(frame, "You are already subscribed to " + club + ".");
                        } else {
                            // Subscribe the user to the club
                            ps = connection
                                    .prepareStatement("INSERT INTO participate (NumClub,CIN, Date) VALUES (?, ?, ?)");
                            ps.setInt(1, optionIndex);
                            ps.setString(2, CIN);
                            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(frame, "You have subscribed to " + club + ".");
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

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(100, 50));
        return button;
    }
}
