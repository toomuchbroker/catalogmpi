package controller;

import dao.TeacherDao;
import model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:4200")  // Adjust the allowed origin as needed for your frontend
public class TeacherController {

    @Autowired
    private TeacherDao teacherDao;

    // Retrieve all teachers
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherDao.getAll();
    }

    // Retrieve a teacher by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
        Teacher teacher = teacherDao.findById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Insert a new teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        teacherDao.insert(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacher);
    }
}
