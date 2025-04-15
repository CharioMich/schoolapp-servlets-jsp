package gr.aueb.cf.schoolapp.dto.student;

import gr.aueb.cf.schoolapp.dto.student.BaseStudentDTO;

public class StudentInsertDTO extends BaseStudentDTO {

    public StudentInsertDTO() {}

    public StudentInsertDTO(String firstname, String lastname, String email, Integer cityId) {
        super(firstname, lastname, email, cityId);
    }
}
