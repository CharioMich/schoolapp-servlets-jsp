package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;

import java.util.List;

public interface IStudentService {
    StudentReadOnlyDTO insertStudent(StudentInsertDTO dto) throws StudentDAOException, StudentAlreadyExistsException;
    StudentReadOnlyDTO updateStudent(Integer id, StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException, StudentAlreadyExistsException;
    void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException;
    StudentReadOnlyDTO getStudentById(Integer id) throws StudentDAOException, StudentNotFoundException;
    List<StudentReadOnlyDTO> getAllStudents() throws StudentDAOException;
    List<StudentReadOnlyDTO> getStudentsByLastname(String lastname) throws StudentDAOException;
    List<StudentReadOnlyDTO> getFilteredStudents(FiltersDTO filters) throws StudentDAOException;
}
