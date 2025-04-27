package backend.controller;

import backend.dao.TeacherDao;
import backend.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private TeacherDao teacherDao;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        logger.info("Fetching all teachers.");
        return teacherDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
        logger.info("Fetching teacher with id: {}", id);
        Teacher teacher = teacherDao.findById(id);
        if (teacher != null) {
            logger.info("Teacher found with id: {}", id);
            return ResponseEntity.ok(teacher);
        } else {
            logger.warn("Teacher not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        teacherDao.insert(teacher);
        logger.info("Teacher created successfully with id: {}", teacher.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(teacher);
    }
}
