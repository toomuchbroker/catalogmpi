package backend.controller;

import backend.dao.CourseDao;
import backend.dao.TeacherDao;
import backend.model.Course;
import backend.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private TeacherDao teacherDao;

    @GetMapping
    public List<Course> getAll() {
        logger.info("Fetching all courses.");
        return courseDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) {
        logger.info("Fetching course with id: {}", id);
        Course course = courseDao.findById(id);
        if (course != null) {
            logger.info("Course found with id: {}", id);
            return ResponseEntity.ok(course);
        } else {
            logger.warn("Course not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course) {
        logger.info("Creating new course: {}", course.getName());
        if (course.getTeacher() == null || course.getTeacher().getId() == 0) {
            logger.warn("Teacher ID missing for course creation.");
            return ResponseEntity.badRequest().body("Teacher ID required");
        }
        Teacher teacher = teacherDao.findById(course.getTeacher().getId());
        if (teacher == null) {
            logger.warn("Teacher not found with id: {}", course.getTeacher().getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Teacher not found");
        }
        course.setTeacher(teacher);
        courseDao.insert(course);
        logger.info("Course created successfully: {}", course.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Course patch) {
        logger.info("Updating course with id: {}", id);
        Course existing = courseDao.findById(id);
        if (existing == null) {
            logger.warn("Course to update not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }

        existing.setName(patch.getName());
        existing.setDescription(patch.getDescription());
        courseDao.update(existing);
        logger.info("Course updated successfully with id: {}", id);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        logger.info("Deleting course with id: {}", id);
        Course existing = courseDao.findById(id);
        if (existing == null) {
            logger.warn("Course to delete not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }

        courseDao.delete(id);
        logger.info("Course deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enroll(@RequestBody Map<String, Integer> data) {
        Integer courseId = data.get("courseId");
        Integer studentId = data.get("studentId");
        if (courseId == null || studentId == null) {
            logger.warn("Missing courseId or studentId for enrollment.");
            return ResponseEntity.badRequest().body("Missing courseId or studentId");
        }
        try {
            courseDao.enrollStudent(studentId, courseId);
            logger.info("Student with id {} enrolled in course id {}", studentId, courseId);
            return ResponseEntity.ok("Student enrolled successfully.");
        } catch (Exception e) {
            logger.error("Enrollment failed for student id {} in course id {}: {}", studentId, courseId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Enrollment failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/unenroll")
    public ResponseEntity<String> unenroll(@RequestParam int courseId,
            @RequestParam int studentId) {
        try {
            courseDao.unenrollStudent(studentId, courseId);
            logger.info("Student with id {} unenrolled from course id {}", studentId, courseId);
            return ResponseEntity.ok("Student unenrolled successfully.");
        } catch (Exception e) {
            logger.error("Unenrollment failed for student id {} from course id {}: {}", studentId, courseId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unenrollment failed: " + e.getMessage());
        }
    }
}
