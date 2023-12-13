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
        panel.setLayout(new GridLayout(6, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addStudentButton = new JButton("Add Student");
        JButton addTeacherButton = new JButton("Add Teacher");
        JButton addCourseButton = new JButton("Add Course");
        JButton addClassroomButton = new JButton("Add Classroom");
        JButton addSectionButton = new JButton("Add Section");
        JButton previousPageButton = new JButton("Previous Page");

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin.main(new String[] {});
                frame.dispose();
            }
        });


        panel.add(addStudentButton);
        panel.add(addTeacherButton);
        panel.add(addCourseButton);
        panel.add(addClassroomButton);
        panel.add(addSectionButton);
        panel.add(previousPageButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    
}
