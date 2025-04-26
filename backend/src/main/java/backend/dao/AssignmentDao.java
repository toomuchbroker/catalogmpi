package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import backend.model.Assignment;

import java.util.List;

@Repository
@Transactional
public class AssignmentDao {

    @PersistenceContext
    private EntityManager em;

    // ✅ Insert new assignment
    public void insert(Assignment assignment) {
        em.persist(assignment);
    }

    // ✅ Find by ID
    public Assignment findById(int id) {
        return em.find(Assignment.class, id);
    }

    // ✅ Get all
    public List<Assignment> getAll() {
        return em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();
    }

    // ✅ Update existing assignment
    public void update(Assignment assignment) {
        em.merge(assignment);
    }

    // ✅ Delete assignment by ID
    public void delete(int id) {
        Assignment assignment = findById(id);
        if (assignment != null) {
            em.remove(assignment);
        }
    }

}
