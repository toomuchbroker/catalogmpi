package backend.dao;

import backend.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class StudentDao {

    @PersistenceContext
    private EntityManager em;

    private static final TupleTransformer<Map<String, Object>> aliasToMapTransformer = (tuple, aliases) -> {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < aliases.length; i++) {
            result.put(aliases[i], tuple[i]);
        }
        return result;
    };

    public List<Map<String, Object>> getEnrollmentsForUser(int userId) {
        return em.createNativeQuery(
                "SELECT c.* FROM courses c " +
                        "JOIN enrollments e ON c.id = e.course_id " +
                        "WHERE e.student_id = (SELECT id FROM students WHERE user_id = :id)")
                .setParameter("id", userId)
                .unwrap(NativeQuery.class)
                .setTupleTransformer(aliasToMapTransformer)
                .getResultList();
    }

    public List<Map<String, Object>> getDetailedGradesForUser(int userId) {
        return em.createNativeQuery(
                "SELECT a.title AS assignment_title, c.name AS course_name, g.value, g.date_assigned " +
                        "FROM grades g " +
                        "JOIN assignments a ON g.assignment_id = a.id " +
                        "JOIN courses c ON a.course_id = c.id " +
                        "WHERE g.student_id = (SELECT id FROM students WHERE user_id = :id)")
                .setParameter("id", userId)
                .unwrap(NativeQuery.class)
                .setTupleTransformer(aliasToMapTransformer)
                .getResultList();
    }

    public List<Map<String, Object>> getAssignmentsForUser(int userId) {
        return em.createNativeQuery(
                "SELECT a.* FROM assignments a " +
                        "JOIN courses c ON a.course_id = c.id " +
                        "JOIN enrollments e ON e.course_id = c.id " +
                        "WHERE e.student_id = (SELECT id FROM students WHERE user_id = :id)")
                .setParameter("id", userId)
                .unwrap(NativeQuery.class)
                .setTupleTransformer(aliasToMapTransformer)
                .getResultList();
    }

    public Student findById(int id) {
        return em.find(Student.class, id);
    }
}
