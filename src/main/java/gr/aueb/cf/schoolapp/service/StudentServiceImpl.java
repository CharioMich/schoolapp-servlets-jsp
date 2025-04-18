package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.student.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // TODO

    @Override
    public StudentReadOnlyDTO insertStudent(StudentInsertDTO dto) throws StudentDAOException, StudentAlreadyExistsException {
        Student student;
        Student insertedStudent;

        try {
            student = Mapper.mapStudentInsertToModel(dto);
            if (studentDAO.getByEmail(student.getEmail()) != null) {
                throw new StudentAlreadyExistsException("Student with email " + student.getEmail() + " already exists.");
            }
            insertedStudent = studentDAO.insert(student);
            return Mapper.mapStudentToReadOnlyDTO(insertedStudent).orElse(null);
        } catch (StudentDAOException | StudentAlreadyExistsException e) {
            //e.printStackTrace();
            // logging
            // rollback
            throw e;
        }
    }

    @Override
    public StudentReadOnlyDTO updateStudent(StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException, StudentAlreadyExistsException {
        return null;
    }

    @Override
    public void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException {

    }

    @Override
    public StudentReadOnlyDTO getStudentById(Integer id) throws StudentDAOException, StudentNotFoundException {
        return null;
    }

    @Override
    public List<StudentReadOnlyDTO> getAllStudents() throws StudentDAOException {
        List<Student> students;

        try {
            students = studentDAO.getAll();
            return students.stream()
                    .map(Mapper::mapStudentToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());

        } catch (StudentDAOException e) {
            throw e;
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByLastname(String lastname) throws StudentDAOException {
        List<Student> students;

        try {
            students = studentDAO.getByLastName(lastname);
            return students.stream()
                    .map(Mapper::mapStudentToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());

        } catch (StudentDAOException e) {
            throw e;
        }
    }
}
