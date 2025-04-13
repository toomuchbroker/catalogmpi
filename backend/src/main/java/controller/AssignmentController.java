package controller;

import dao.AssignmentDao;
import model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:4200") // Adjust the allowed origin as needed.
public class AssignmentController {

    @Autowired
    private AssignmentDao assignmentDao;

    // Retrieve all assignments
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentDao.getAll();
    }

    // Retrieve a single assignment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int id) {
        Assignment assignment = assignmentDao.findById(id);
        if (assignment != null) {
            return ResponseEntity.ok(assignment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Insert a new assignment
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        assignmentDao.insert(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }
}
