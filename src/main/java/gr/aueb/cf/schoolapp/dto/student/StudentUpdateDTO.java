package gr.aueb.cf.schoolapp.dto.student;

import gr.aueb.cf.schoolapp.dto.student.BaseStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StudentUpdateDTO extends BaseStudentDTO {
    private Integer id;

    public StudentUpdateDTO() {

    }

    public StudentUpdateDTO(Integer id, String firstname, String lastname, String email, Integer cityId) {
        super(firstname, lastname, email, cityId);
        this.id = id;
    }

    @Override
    public String toString() {
        return "StudentUpdateDTO{" +
                "id=" + id +
                '}' + super.toString();
    }
}
