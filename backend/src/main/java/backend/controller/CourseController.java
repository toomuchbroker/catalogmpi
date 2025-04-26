package backend.controller;

import backend.dao.CourseDao;
import backend.dao.TeacherDao;
import backend.model.Course;
import backend.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    @Autowired private CourseDao courseDao;
    @Autowired private TeacherDao teacherDao;

    @GetMapping
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) {
        Course c = courseDao.findById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course in) {
        if (in.getTeacher() == null || in.getTeacher().getId() == 0) {
            return ResponseEntity.badRequest().body("Teacher ID required");
        }
        Teacher t = teacherDao.findById(in.getTeacher().getId());
        if (t == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Teacher not found");
        }
        in.setTeacher(t);
        courseDao.insert(in);
        return ResponseEntity.status(HttpStatus.CREATED).body(in);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Course patch) {
        Course e = courseDao.findById(id);
        if (e == null) return ResponseEntity.notFound().build();

        e.setName(patch.getName());
        e.setDescription(patch.getDescription());
        courseDao.update(e);
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Course e = courseDao.findById(id);
        if (e == null) return ResponseEntity.notFound().build();

        courseDao.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(@RequestBody Map<String, Integer> data) {
        Integer courseId = data.get("courseId");
        Integer userId   = data.get("studentId");
        if (courseId == null || userId == null)
            return ResponseEntity.badRequest().body("Missing courseId or studentId");
        try {
            courseDao.enrollStudent(userId, courseId);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Enrollment failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/unenroll")
    public ResponseEntity<String> unenroll(@RequestParam int courseId,
                                           @RequestParam int studentId) {
        try {
            courseDao.unenrollStudent(studentId, courseId);
            return ResponseEntity.ok("Student unenrolled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unenrollment failed: " + e.getMessage());
        }
    }
}
