package backend.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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

    public List<Map<String, Object>> getGradesForUser(int userId) {
        return em.createNativeQuery(
                "SELECT g.* FROM grades g " +
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
}
