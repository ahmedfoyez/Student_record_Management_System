import java.sql.*;
import java.util.*;

public class StudentOperations {

    public static void addStudent(String name, int age, String grade, String email) {
        String query = "INSERT INTO students (name, age, grade, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, grade);
            ps.setString(4, email);
            ps.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade"),
                        rs.getString("email")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, studentId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentId + " deleted successfully.");
            } else {
                System.out.println("No student found with ID " + studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
