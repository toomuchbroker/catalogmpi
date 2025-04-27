package backend.dao;

import backend.model.Assignment;
import backend.model.Grade;
import backend.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class GradeDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Grade grade) {
        em.persist(grade);
    }

    public Grade findById(int id) {
        return em.find(Grade.class, id);
    }

    public List<Grade> getAll() {
        return em.createQuery("SELECT g FROM Grade g", Grade.class).getResultList();
    }

    public void update(Grade grade) {
        em.merge(grade);
    }

    public void delete(int id) {
        Grade grade = findById(id);
        if (grade != null) {
            em.remove(grade);
        }
    }

    public Double getAverageGradeForStudent(int userId) {
        return em.createQuery(
                "SELECT AVG(g.value) FROM Grade g WHERE g.student.user.id = :userId", Double.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public String insertGradeWithUserId(double value, int assignmentId, int userId) {
        try {
            Assignment assignment = em.find(Assignment.class, assignmentId);
            if (assignment == null) {
                return "Invalid assignment ID.";
            }

            Integer studentId = (Integer) em.createQuery(
                    "SELECT s.id FROM Student s WHERE s.user.id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();

            Student student = em.find(Student.class, studentId);
            if (student == null) {
                return "Invalid student (user) ID.";
            }

            Grade grade = new Grade();
            grade.setValue(value);
            grade.setAssignment(assignment);
            grade.setStudent(student);
            grade.setDateAssigned(new Date(System.currentTimeMillis()));

            em.persist(grade);

            return "Grade created successfully.";
        } catch (Exception e) {
            return "Failed to create grade: " + e.getMessage();
        }
    }
}
