package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.student.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.*;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.*;
import gr.aueb.cf.schoolapp.validator.StudentValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/school-app/students/update")
public class StudentUpdateController extends HttpServlet {

    IStudentDAO studentDAO = new StudentDAOIMpl();
    IStudentService studentService = new StudentServiceImpl(studentDAO);
    ICityDAO cityDAO = new CityDAOImpl();
    ICityService cityService = new CityServiceImpl(cityDAO);
    StudentUpdateDTO updateDTO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<City> cities = null;
        Integer id = Integer.parseInt(req.getParameter("id").trim());
        try {
            cities = cityService.getAllCities();
            StudentReadOnlyDTO studentReadOnlyDTO = studentService.getStudentById(id);
            req.setAttribute("cities", cities);
            if (req.getSession().getAttribute("updateDTO") != null) {
                // Move from session to request scope for JSP
                req.setAttribute("updateDTO", req.getSession().getAttribute("updateDTO"));

                // Clear session data (so it doesn't persist after refresh)
                req.getSession().removeAttribute("updateDTO");
            }
            else req.setAttribute("updateDTO", studentReadOnlyDTO);
            req.getRequestDispatcher("/WEB-INF/jsp/student-update.jsp").forward(req, resp);
        } catch (SQLException | StudentDAOException | StudentNotFoundException e) {
            String errorMessage = e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("/WEB-INF/jsp/student-update.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> errors;
        String firstnameMessage;
        String lastnameMessage;
        String emailMessage;
        String cityIdMessage;
        String errorMessage;
        Student student;

        // Data binding
        String idStr = (req.getParameter("id") != null) ? req.getParameter("id").trim() : "";
        Integer id = Integer.parseInt(idStr);
        String firstname = (req.getParameter("firstname") != null) ? req.getParameter("firstname").trim() : "";
        String lastname = (req.getParameter("lastname") != null) ? req.getParameter("lastname").trim() : "";
        String email = (req.getParameter("email") != null) ? req.getParameter("email").trim() : "";
        Integer cityId = (req.getParameter("cityId") != null) ? Integer.parseInt(req.getParameter("cityId").trim()) : 0;
        updateDTO = new StudentUpdateDTO(id, firstname, lastname, email, cityId);


        try {
            // Validate dto
            errors = StudentValidator.validate(updateDTO);

            if (!errors.isEmpty()) {
                firstnameMessage = errors.getOrDefault("firstname", "");
                lastnameMessage = errors.getOrDefault("lastname", "");
                // more ...

                req.getSession().setAttribute("firstnameMessage", firstnameMessage);
                req.getSession().setAttribute("lastnameMessage", lastnameMessage);
                req.getSession().setAttribute("updateDTO", updateDTO);
                resp.sendRedirect(req.getContextPath() + "/school-app/students/update?id=" + id);
                return;
            }

            // Call the service

            StudentReadOnlyDTO readOnlyDTO = studentService.updateStudent(id, updateDTO);
            HttpSession session = req.getSession(false);
            session.setAttribute("teacherInfo", readOnlyDTO);
            // PRG Pattern
            resp.sendRedirect(req.getContextPath() + "/school-app/teacher-updated");

        } catch (StudentDAOException | StudentAlreadyExistsException | StudentNotFoundException e) {
            errorMessage = e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("/WEB-INF/jsp/students.jsp")
                    .forward(req, resp);
        }
    }
}
