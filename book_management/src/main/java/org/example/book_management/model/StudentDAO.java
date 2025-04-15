package org.example.book_management.model;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection = DBConnection.connect();

    public void addStudent(String name, String studentClass) throws SQLException {
        String query = "INSERT INTO Student (full_name, class) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, studentClass);
            stmt.executeUpdate();
        }
    }

    public String getStudentById(int id) throws SQLException {
        String query = "SELECT * FROM Student WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Name");
                }
            }
        }
        return null;
    }
    public List<Student> getStudentList() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM Student";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("student_id"));
                    student.setName(rs.getString("full_name"));
                    student.setClassName(rs.getString("class"));
                    studentList.add(student);
                }
            }
        }
        return studentList;
    }
}
