package backend.controller;

import backend.dao.GradeDao;
import backend.dao.AssignmentDao;
import backend.dao.StudentDao;
import backend.model.Grade;
import backend.model.Assignment;
import backend.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "http://localhost:4200") // adjust if needed
public class GradeController {

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private StudentDao studentDao;

    // ✅ Get all grades
    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeDao.getAll();
    }

    // ✅ Get grade by id (optional, useful later)
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable int id) {
        Grade grade = gradeDao.findById(id);
        return grade != null ? ResponseEntity.ok(grade) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createGrade(@RequestBody Map<String, Object> data) {
        try {
            double value = Double.parseDouble(data.get("value").toString());
            int assignmentId = Integer.parseInt(data.get("assignmentId").toString());
            int userId = Integer.parseInt(data.get("studentId").toString());

            String result = gradeDao.insertGradeWithUserId(value, assignmentId, userId);

            if (result.contains("successfully")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage());
        }
    }

    // ✅ Update existing grade
    @PutMapping("/{id}")
    public ResponseEntity<String> updateGrade(@PathVariable int id, @RequestBody Map<String, Object> data) {
        try {
            Grade existingGrade = gradeDao.findById(id);
            if (existingGrade == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grade not found.");
            }

            // Update the simple fields
            if (data.containsKey("value")) {
                existingGrade.setValue(Double.parseDouble(data.get("value").toString()));
            }
            if (data.containsKey("date_assigned")) {
                existingGrade.setDateAssigned(Date.valueOf(data.get("date_assigned").toString()));
            }

            // Update student (only if student_id is provided)
            if (data.containsKey("student_id")) {
                int studentId = Integer.parseInt(data.get("student_id").toString());
                Student student = studentDao.findById(studentId);
                existingGrade.setStudent(student);
            }

            // Update assignment (only if assignment_id is provided)
            if (data.containsKey("assignment_id")) {
                int assignmentId = Integer.parseInt(data.get("assignment_id").toString());
                Assignment assignment = assignmentDao.findById(assignmentId);
                existingGrade.setAssignment(assignment);
            }

            // Save changes
            gradeDao.update(existingGrade);

            return ResponseEntity.ok("Grade updated successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update grade: " + e.getMessage());
        }
    }

    // ✅ Delete grade
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable int id) {
        Grade grade = gradeDao.findById(id);
        if (grade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grade not found.");
        }

        gradeDao.delete(id);
        return ResponseEntity.ok("Grade deleted successfully.");
    }

    @GetMapping("/average/{studentId}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable int studentId) {
        Double average = gradeDao.getAverageGradeForStudent(studentId);
        return ResponseEntity.ok(average != null ? average : 0.0);
    }
}
