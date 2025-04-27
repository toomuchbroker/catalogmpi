package backend.controller;

import backend.dao.AssignmentDao;
import backend.model.Assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:4200")
public class AssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private AssignmentDao assignmentDao;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        logger.info("Fetching all assignments.");
        return assignmentDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int id) {
        logger.info("Fetching assignment with id: {}", id);
        Assignment assignment = assignmentDao.findById(id);
        if (assignment != null) {
            logger.info("Assignment found with id: {}", id);
            return ResponseEntity.ok(assignment);
        } else {
            logger.warn("Assignment not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
        assignmentDao.insert(assignment);
        logger.info("Created new assignment with title: {}", assignment.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body("Assignment created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable int id, @RequestBody Assignment updatedAssignment) {
        logger.info("Updating assignment with id: {}", id);
        Assignment existing = assignmentDao.findById(id);
        if (existing == null) {
            logger.warn("Assignment to update not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }

        existing.setTitle(updatedAssignment.getTitle());
        existing.setDescription(updatedAssignment.getDescription());
        existing.setDueDate(updatedAssignment.getDueDate());
        existing.setCourse(updatedAssignment.getCourse());

        assignmentDao.update(existing);
        logger.info("Assignment updated successfully with id: {}", id);
        return ResponseEntity.ok("Assignment updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable int id) {
        logger.info("Deleting assignment with id: {}", id);
        Assignment assignment = assignmentDao.findById(id);
        if (assignment == null) {
            logger.warn("Assignment to delete not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found.");
        }

        assignmentDao.delete(id);
        logger.info("Assignment deleted successfully with id: {}", id);
        return ResponseEntity.ok("Assignment deleted successfully.");
    }
}
