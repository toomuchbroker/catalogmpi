package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import backend.dao.StudentDao;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200") // Adjust the allowed origin as needed
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    // Retrieve all students
    @GetMapping("/{id}/enrollments")
    public List<Map<String, Object>> getEnrollments(@PathVariable int id) {
        return studentDao.getEnrollmentsForUser(id);
    }

    @GetMapping("/{id}/grades-detailed")
    public List<Map<String, Object>> getDetailedGrades(@PathVariable int id) {
        return studentDao.getDetailedGradesForUser(id);
    }

    @GetMapping("/{id}/assignments")
    public List<Map<String, Object>> getAssignments(@PathVariable int id) {
        return studentDao.getAssignmentsForUser(id);
    }
}
