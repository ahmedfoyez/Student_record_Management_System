import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementSystem {

    private JFrame frame;
    private JTextField nameField, ageField, gradeField, emailField, deleteIdField;
    private JTextArea outputArea;
    private JScrollPane scrollPane;

    public StudentManagementSystem() {
        frame = new JFrame("Student Records Management System");

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        gradeField = new JTextField(20);
        emailField = new JTextField(20);
        deleteIdField = new JTextField(20);

        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View All Students");
        JButton deleteButton = new JButton("Delete Student");

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);  // Ensure that text wraps
        outputArea.setWrapStyleWord(true);  // Word wrap

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));  // Grid for input fields and labels
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(addButton);
        panel.add(viewButton);

        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new FlowLayout());
        deletePanel.add(new JLabel("Student ID to Delete:"));
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteButton);


        frame.add(panel);
        frame.add(deletePanel);
        frame.add(new JLabel("Student Records:"));
        scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane);

        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addButton.addActionListener(e -> addStudent());
        viewButton.addActionListener(e -> viewAllStudents());
        deleteButton.addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        SwingWorker<Void, Void> addStudentWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String grade = gradeField.getText();
                String email = emailField.getText();
                StudentOperations.addStudent(name, age, grade, email);
                return null;
            }

            @Override
            protected void done() {
                clearFields();
                JOptionPane.showMessageDialog(frame, "Student added successfully!");
            }
        };
        addStudentWorker.execute();
    }

    private void viewAllStudents() {
        SwingWorker<String, Void> viewStudentsWorker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                StringBuilder sb = new StringBuilder();
                List<Student> students = StudentOperations.getAllStudents();
                if (students.isEmpty()) {
                    sb.append("No student records found.");
                } else {
                    for (Student student : students) {
                        sb.append("ID: ").append(student.getId())
                                .append("\nName: ").append(student.getName())
                                .append("\nAge: ").append(student.getAge())
                                .append("\nGrade: ").append(student.getGrade())
                                .append("\nEmail: ").append(student.getEmail())
                                .append("\n-----------------------------------------\n");
                    }
                }
                return sb.toString();
            }

            @Override
            protected void done() {
                try {
                    String records = get();
                    outputArea.setText(records);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        viewStudentsWorker.execute();
    }

    private void deleteStudent() {
        SwingWorker<Void, Void> deleteStudentWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    int studentId = Integer.parseInt(deleteIdField.getText());
                    StudentOperations.deleteStudent(studentId);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid student ID.");
                }
                return null;
            }

            @Override
            protected void done() {

                deleteIdField.setText("");
                JOptionPane.showMessageDialog(frame, "Student deleted successfully!");
                viewAllStudents();
            }
        };
        deleteStudentWorker.execute();
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        gradeField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}

