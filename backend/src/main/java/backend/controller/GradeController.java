package backend.controller;

import backend.dao.AssignmentDao;
import backend.dao.GradeDao;
import backend.dao.StudentDao;
import backend.model.Assignment;
import backend.model.Grade;
import backend.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "http://localhost:4200")
public class GradeController {

    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private StudentDao studentDao;

    @GetMapping
    public List<Grade> getAllGrades() {
        logger.info("Fetching all grades.");
        return gradeDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable int id) {
        logger.info("Fetching grade with id: {}", id);
        Grade grade = gradeDao.findById(id);
        if (grade != null) {
            logger.info("Grade found with id: {}", id);
            return ResponseEntity.ok(grade);
        } else {
            logger.warn("Grade not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createGrade(@RequestBody Map<String, Object> data) {
        try {
            double value = Double.parseDouble(data.get("value").toString());
            int assignmentId = Integer.parseInt(data.get("assignmentId").toString());
            int studentId = Integer.parseInt(data.get("studentId").toString());

            String result = gradeDao.insertGradeWithUserId(value, assignmentId, studentId);

            if (result.contains("successfully")) {
                logger.info("Grade created successfully for student id: {}", studentId);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                logger.warn("Failed to create grade: {}", result);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } catch (Exception e) {
            logger.error("Error creating grade: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGrade(@PathVariable int id, @RequestBody Map<String, Object> data) {
        try {
            logger.info("Updating grade with id: {}", id);
            Grade existingGrade = gradeDao.findById(id);
            if (existingGrade == null) {
                logger.warn("Grade to update not found with id: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grade not found.");
            }

            if (data.containsKey("value")) {
                existingGrade.setValue(Double.parseDouble(data.get("value").toString()));
            }
            if (data.containsKey("date_assigned")) {
                existingGrade.setDateAssigned(Date.valueOf(data.get("date_assigned").toString()));
            }
            if (data.containsKey("student_id")) {
                int studentId = Integer.parseInt(data.get("student_id").toString());
                Student student = studentDao.findById(studentId);
                existingGrade.setStudent(student);
            }
            if (data.containsKey("assignment_id")) {
                int assignmentId = Integer.parseInt(data.get("assignment_id").toString());
                Assignment assignment = assignmentDao.findById(assignmentId);
                existingGrade.setAssignment(assignment);
            }

            gradeDao.update(existingGrade);
            logger.info("Grade updated successfully with id: {}", id);
            return ResponseEntity.ok("Grade updated successfully.");

        } catch (Exception e) {
            logger.error("Error updating grade with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update grade: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable int id) {
        logger.info("Deleting grade with id: {}", id);
        Grade grade = gradeDao.findById(id);
        if (grade == null) {
            logger.warn("Grade to delete not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grade not found.");
        }

        gradeDao.delete(id);
        logger.info("Grade deleted successfully with id: {}", id);
        return ResponseEntity.ok("Grade deleted successfully.");
    }

    @GetMapping("/average/{studentId}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable int studentId) {
        logger.info("Fetching average grade for student id: {}", studentId);
        Double average = gradeDao.getAverageGradeForStudent(studentId);
        return ResponseEntity.ok(average != null ? average : 0.0);
    }
}
