package controller;

import dao.CourseDao;
import model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200") // Adjust the allowed origin as needed
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    // Retrieve a list of all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseDao.getAll();
    }

    // Retrieve a single course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseDao.findById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Insert a new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseDao.insert(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }
}
