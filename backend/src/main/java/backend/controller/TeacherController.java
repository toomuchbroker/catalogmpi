package backend.controller;

import backend.dao.TeacherDao;
import backend.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                         // <- add this line
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    @Autowired
    private TeacherDao teacherDao;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
        Teacher teacher = teacherDao.findById(id);
        return teacher != null
                ? ResponseEntity.ok(teacher)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        teacherDao.insert(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacher);
    }
}
