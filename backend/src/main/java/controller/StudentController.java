package controller;

import dao.StudentDao;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")  // Adjust the allowed origin as needed
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    // Retrieve all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentDao.getAll();
    }

    // Retrieve a student by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Student student = studentDao.findById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Insert a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentDao.insert(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }
}
