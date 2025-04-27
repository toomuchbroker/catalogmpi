package backend.dao;

import backend.model.Assignment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AssignmentDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Assignment assignment) {
        em.persist(assignment);
    }

    public Assignment findById(int id) {
        return em.find(Assignment.class, id);
    }

    public List<Assignment> getAll() {
        return em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();
    }

    public void update(Assignment assignment) {
        em.merge(assignment);
    }

    public void delete(int id) {
        Assignment assignment = findById(id);
        if (assignment != null) {
            em.remove(assignment);
        }
    }
}
