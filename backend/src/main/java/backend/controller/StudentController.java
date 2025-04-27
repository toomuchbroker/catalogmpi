package backend.controller;

import backend.dao.StudentDao;
import backend.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudentDao studentDao;

    @GetMapping("/{id}/enrollments")
    public List<Map<String, Object>> getEnrollments(@PathVariable int id) {
        logger.info("Fetching enrollments for student id: {}", id);
        return studentDao.getEnrollmentsForUser(id);
    }

    public Student findStudentById(int id) {
        logger.info("Finding student by id: {}", id);
        return em.find(Student.class, id);
    }

    @GetMapping("/{id}/grades-detailed")
    public List<Map<String, Object>> getDetailedGrades(@PathVariable int id) {
        logger.info("Fetching detailed grades for student id: {}", id);
        return studentDao.getDetailedGradesForUser(id);
    }

    @GetMapping("/{id}/assignments")
    public List<Map<String, Object>> getAssignments(@PathVariable int id) {
        logger.info("Fetching assignments for student id: {}", id);
        return studentDao.getAssignmentsForUser(id);
    }
}
