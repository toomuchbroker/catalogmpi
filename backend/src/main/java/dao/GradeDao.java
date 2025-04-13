package dao;

import jakarta.persistence.EntityManager;
import model.Grade;
import java.util.List;

public class GradeDao {
    private EntityManager em;

    public GradeDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Grade grade) {
        em.getTransaction().begin();
        em.persist(grade);
        em.getTransaction().commit();
    }

    public List<Grade> getAll() {
        return em.createQuery("SELECT g FROM Grade g", Grade.class).getResultList();
    }
}
