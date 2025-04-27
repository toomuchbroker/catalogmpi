package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import backend.model.Grade;
import backend.model.Assignment;
import backend.model.Student;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class GradeDao {

    @PersistenceContext
    private EntityManager em;

    // ✅ Save a new grade
    public void insert(Grade grade) {
        em.persist(grade);
    }

    // ✅ Find by ID
    public Grade findById(int id) {
        return em.find(Grade.class, id);
    }

    // ✅ Get all grades
    public List<Grade> getAll() {
        return em.createQuery("SELECT g FROM Grade g", Grade.class).getResultList();
    }

    // ✅ Update existing grade
    public void update(Grade grade) {
        em.merge(grade);
    }

    // ✅ Delete grade
    public void delete(int id) {
        Grade grade = findById(id);
        if (grade != null) {
            em.remove(grade);
        }
    }

    // ✅ Get average grade for a student (by USER ID)
    public Double getAverageGradeForStudent(int userId) {
        return em.createQuery(
                "SELECT AVG(g.value) FROM Grade g WHERE g.student.user.id = :userId", Double.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    // ✅ Insert Grade based on user ID and assignment ID
    public String insertGradeWithUserId(double value, int assignmentId, int userId) {
        try {
            // Find the Assignment
            Assignment assignment = em.find(Assignment.class, assignmentId);
            if (assignment == null) {
                return "Invalid assignment ID.";
            }

            // Find the Student by userId
            Integer studentId = (Integer) em.createQuery(
                    "SELECT s.id FROM Student s WHERE s.user.id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();

            Student student = em.find(Student.class, studentId);
            if (student == null) {
                return "Invalid student (user) ID.";
            }

            // Create the Grade
            Grade grade = new Grade();
            grade.setValue(value);
            grade.setAssignment(assignment);
            grade.setStudent(student);
            grade.setDateAssigned(new Date(System.currentTimeMillis())); // Set today's date

            em.persist(grade);

            return "Grade created successfully.";
        } catch (Exception e) {
            return "Failed to create grade: " + e.getMessage();
        }
    }
}
