package dao;

import jakarta.persistence.EntityManager;
import model.Assignment;
import model.Student;

import java.util.Date;
import java.util.List;

public class AssignmentDao {
    private EntityManager em;
    private Date deadline;

    public AssignmentDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Assignment assignment) {
        em.getTransaction().begin();
        em.persist(assignment);
        em.getTransaction().commit();
    }

    public Assignment findById(int id) {
        return em.find(Assignment.class, id);
    }

    public Date getDeadline() {
        return deadline;
    }

    public List<Assignment> getAll() {
        return em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();
    }
}
