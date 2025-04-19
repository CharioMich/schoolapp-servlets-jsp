package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

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
    public StudentReadOnlyDTO updateStudent(Integer id, StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException, StudentAlreadyExistsException {
        Student student;
        Student insertedStudent;

        try {
            if (studentDAO.getById(id) == null)
                throw new StudentNotFoundException("Error. Student with id " + dto.getId() + " not found.");

            student = studentDAO.getByEmail(dto.getEmail());
            if (student != null && !student.getId().equals(id))
                throw new StudentAlreadyExistsException("Student with id " + id + " already exists.");

            insertedStudent = studentDAO.update(Mapper.mapStudentUpdateToModel(dto));
            // some logging
            return Mapper.mapStudentToReadOnlyDTO(insertedStudent).orElse(null);

        } catch (StudentDAOException | StudentNotFoundException | StudentAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException {

        try {
            if (studentDAO.getById(id) == null)
                throw new StudentNotFoundException("Student with id " + id + " not found.");

            studentDAO.delete(id);
        } catch (StudentDAOException | StudentNotFoundException e) {
            throw e;
        }
    }

    @Override
    public StudentReadOnlyDTO getStudentById(Integer id) throws StudentDAOException, StudentNotFoundException {
        Student student;

        try {
            student = studentDAO.getById(id);
            return Mapper.mapStudentToReadOnlyDTO(student).orElseThrow(() -> new StudentNotFoundException("Student with id" + id + " not found"));
        } catch (StudentDAOException | StudentNotFoundException e) {
            throw e;
        }
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

    @Override
    public List<StudentReadOnlyDTO> getFilteredStudents(FiltersDTO filters) throws StudentDAOException {
        List<Student> students;
        try {
            students = studentDAO.getFilteredStudents(filters.getFirstname(), filters.getLastname());
            return students.stream()
                    .map(Mapper::mapStudentToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (StudentDAOException e) {
            throw e;
        }
    }
}
