package dao;

import jakarta.persistence.EntityManager;
import model.Assignment;
import java.util.List;

public class AssignmentDao {
    private EntityManager em;

    public AssignmentDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Assignment assignment) {
        em.getTransaction().begin();
        em.persist(assignment);
        em.getTransaction().commit();
    }

    public List<Assignment> getAll() {
        return em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();
    }
}
