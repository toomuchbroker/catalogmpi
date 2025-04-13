package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Assignment;
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
}
