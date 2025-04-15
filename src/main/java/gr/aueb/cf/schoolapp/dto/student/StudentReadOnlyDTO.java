package gr.aueb.cf.schoolapp.dto.student;

import gr.aueb.cf.schoolapp.dto.student.BaseStudentDTO;


public class StudentReadOnlyDTO extends BaseStudentDTO {
    private Integer id;
    private String uuid;

    public StudentReadOnlyDTO() {

    }

    public StudentReadOnlyDTO(Integer id, String uuid, String firstname, String lastname, String email, Integer cityId) {
        super(firstname, lastname, email, cityId);
        this.id = id;
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "StudentReadOnlyDTO{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
