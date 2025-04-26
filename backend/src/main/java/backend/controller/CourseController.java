package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.dao.CourseDao;
import backend.model.Course;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseDao.getAll();
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        courseDao.insert(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseDao.findById(id);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestBody Map<String, Integer> data) {
        Integer courseId = data.get("courseId");
        Integer userId = data.get("studentId"); // Still referring to user_id

        if (courseId == null || userId == null) {
            return ResponseEntity.badRequest().body("Missing courseId or studentId");
        }

        System.out.println("Hello" + userId + " " + courseId);

        try {
            courseDao.enrollStudent(userId, courseId);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Enrollment failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/unenroll")
    public ResponseEntity<String> unenrollStudent(@RequestParam int courseId, @RequestParam int studentId) {
        try {
            courseDao.unenrollStudent(studentId, courseId);
            return ResponseEntity.ok("Student unenrolled successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unenrollment failed: " + e.getMessage());
        }
    }
}
