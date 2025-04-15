package gr.aueb.cf.schoolapp.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseStudentDTO {
    private String firstname;
    private String lastname;
    private String email;
    private Integer cityId;

    @Override
    public String toString() {
        return "BaseStudentDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
