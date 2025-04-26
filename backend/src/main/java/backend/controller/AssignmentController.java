package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.dao.AssignmentDao;
import backend.model.Assignment;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:4200") // Adjust if needed
public class AssignmentController {

    @Autowired
    private AssignmentDao assignmentDao;

    // ✅ Get all assignments
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentDao.getAll();
    }

    // ✅ Get assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int id) {
        Assignment assignment = assignmentDao.findById(id);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    // ✅ Create new assignment
    @PostMapping
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
        assignmentDao.insert(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Assignment created successfully.");
    }

    // ✅ Update assignment
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable int id, @RequestBody Assignment updatedAssignment) {
        Assignment existing = assignmentDao.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setDueDate(updatedAssignment.getDueDate());
        existing.setCourse(updatedAssignment.getCourse());

        System.out.println(updatedAssignment.getDueDate()
                + "--------------------------------------------------------------------------------------------------");

        assignmentDao.update(existing);
        return ResponseEntity.ok("Assignment updated successfully.");
    }

    // ✅ Delete assignment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable int id) {
        Assignment assignment = assignmentDao.findById(id);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found.");
        }

        assignmentDao.delete(id);
        return ResponseEntity.ok("Assignment deleted successfully.");
    }
}
