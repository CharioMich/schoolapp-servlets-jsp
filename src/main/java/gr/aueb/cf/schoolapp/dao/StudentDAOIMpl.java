package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDAOIMpl implements IStudentDAO {

    @Override
    public Student insert(Student student) throws StudentDAOException {
        String sql = "INSERT INTO students (firstname, lastname, email, city_id, uuid, created_at, updated_at)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Student insertedStudent = new Student();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getLastname());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getCityId());
            ps.setString(5, UUID.randomUUID().toString());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();

            ResultSet rsGeneratedKeys = ps.getGeneratedKeys();
            if (rsGeneratedKeys.next()) {
                int generatedId = rsGeneratedKeys.getInt(1);
                insertedStudent = getById(generatedId);
            }

            return insertedStudent;

        } catch(SQLException e) {
            throw new StudentDAOException("SQL Error in insert Student with email: " + student.getEmail());
        }
    }

    @Override
    public Student update(Student student) throws StudentDAOException {
        String sql = "UPDATE students SET firstname = ?, lastname = ?, email = ?, city_id = ?, updated_at = ?" +
                        "WHERE id = ?";

        Student updatedStudent;

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getLastname());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getCityId());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, student.getId());

            ps.executeUpdate();

            updatedStudent = getById(student.getId());

            // some logging

            return updatedStudent;

        } catch (SQLException e) {
            //e.printStackTrace();
            // logging
            throw new StudentDAOException("Error in updating student with email: " + student.getEmail());
        }
    }

    @Override
    public void delete(Integer id) throws StudentDAOException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            // some logging
        } catch (SQLException e) {
            // e.printStackTrace();
            // some logging
            throw new StudentDAOException("Error in deleting student with id: " + id);
        }
    }

    @Override
    public Student getById(Integer id) throws StudentDAOException {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                        rs.getInt("city_id"), rs.getString("uuid"), rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
            }

            return student;

        } catch(SQLException e) {
            // logging
            // e.printStackTrace();
            throw new StudentDAOException("SQL Error in getting student with id: " + id);
        }
    }

    @Override
    public Student getByEmail(String email) throws StudentDAOException {
        String sql = "SELECT * FROM students WHERE email = ?";
        Student student = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                        rs.getInt("city_id"), rs.getString("uuid"), rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
            }

            return student;

        } catch (SQLException e) {
            // e.printStackTrace();
            // logging
            throw new StudentDAOException("Error in getting student by email " + email);
        }
    }

    @Override
    public List<Student> getAll() throws StudentDAOException {
        String sql = "SELECT * FROM students";
        Student student;
        List<Student> students = new ArrayList<>();
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                        rs.getInt("city_id"), rs.getString("uuid"), rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
                students.add(student);
            }

        } catch (SQLException e) {
            // logging
            // e.printStackTrace();
            throw new StudentDAOException("Error in getting students.");
        }
        return students;
    }

    @Override
    public List<Student> getByLastName(String lastname) throws StudentDAOException {
        String sql = "SELECT * FROM students WHERE lastname = ?";
        Student student;
        List<Student> students = new ArrayList<>();
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, lastname);

            rs = ps.executeQuery();

            while (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                        rs.getInt("city_id"), rs.getString("uuid"), rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
                students.add(student);
            }
        } catch (SQLException e) {
            // logging
            // e.printStackTrace();
            throw new StudentDAOException("Error in getting student(s) by lastname: " + lastname);
        }
        return students;
    }

    @Override
    public List<Student> getFilteredStudents(String firstname, String lastname) throws StudentDAOException {
        String sql = "SELECT * FROM students WHERE firstname LIKE ? AND lastname LIKE ?";
        List<Student> students = new ArrayList<>(); // isEmpty == true
        ResultSet rs;
        Student student;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, firstname + "%");
            ps.setString(2, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getInt("city_id"),
                        rs.getString("uuid"), rs.getTimestamp("created_at").toLocalDateTime(), rs.getTimestamp("updated_at").toLocalDateTime());
                students.add(student);
            }
            // Logging
            students.forEach(System.out::println);
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in filtered get");
        }
    }
}
