package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.student.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // TODO

    @Override
    public StudentReadOnlyDTO insertStudent(StudentInsertDTO dto) throws StudentDAOException, StudentAlreadyExistsException {
        return null;
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
        return List.of();
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByLastname() throws StudentDAOException {
        return List.of();
    }
}
