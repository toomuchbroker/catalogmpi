package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.dao.GradeDao;
import backend.model.Grade;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "http://localhost:4200") // Adjust this to match your frontend's URL
public class GradeController {

    @Autowired
    private GradeDao gradeDao;

    // Endpoint to retrieve all grades
    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeDao.getAll();
    }

    // Endpoint to create a new grade
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        gradeDao.insert(grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(grade);
    }
}
