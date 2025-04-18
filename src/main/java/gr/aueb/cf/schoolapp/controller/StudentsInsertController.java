package gr.aueb.cf.schoolapp.controller;

import com.mysql.cj.protocol.x.XMessage;
import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOIMpl;
import gr.aueb.cf.schoolapp.dto.student.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.CityServiceImpl;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.validator.StudentValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/school-app/students/insert")
public class StudentsInsertController extends HttpServlet {
    IStudentDAO studentDAO = new StudentDAOIMpl();
    IStudentService studentService = new StudentServiceImpl(studentDAO);
    ICityDAO cityDAO = new CityDAOImpl();
    ICityService cityService = new CityServiceImpl(cityDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<City> cities = cityService.getAllCities();
            req.setAttribute("cities", cities);

            if (req.getSession().getAttribute("formData") != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> formData = (Map<String, Object>) req.getSession().getAttribute("formData");

                // formData.forEach((key, value) -> req.setAttribute(key, value));
                formData.forEach(req::setAttribute);
                req.getSession().removeAttribute("formData");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/student-insert.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("errorMessage", "Error in getting cities " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> formData = new HashMap<>();

        StudentInsertDTO studentInsertDTO = new StudentInsertDTO(
                req.getParameter("firstname"),
                req.getParameter("lastname"),
                req.getParameter("email"),
                req.getParameter("cityId") != null ? Integer.parseInt(req.getParameter("cityId")) : 0
        );

        formData.put("insertDTO", studentInsertDTO);

        try {
            Map<String, String> errors = StudentValidator.validate(studentInsertDTO);
            if (!errors.isEmpty()) {
                errors.forEach((field, message) -> formData.put(field + "Message", message));

                req.getSession().setAttribute("formData", formData);
                resp.sendRedirect(req.getContextPath() + "/school-app/students/insert");
                return;
            }

            StudentReadOnlyDTO insertedStudent = studentService.insertStudent(studentInsertDTO);
            req.getSession().setAttribute("personInfo", insertedStudent);
            resp.sendRedirect(req.getContextPath() + "/school-app/students/inserted");

        } catch (StudentDAOException | StudentAlreadyExistsException e) {
            formData.put("errorMessage", e.getMessage());
            req.getSession().setAttribute("formData", formData);
            resp.sendRedirect(req.getContextPath() + "/school-app/students/insert");
        } catch (NumberFormatException e) {
            formData.put("errorMessage", "Invalid city selection");
            req.getSession().setAttribute("formData", formData);
            resp.sendRedirect(req.getContextPath() + "/school-app/students/insert");
        }
    }
}
