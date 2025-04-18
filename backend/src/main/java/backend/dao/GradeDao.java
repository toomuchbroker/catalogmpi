package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.model.Grade;

import java.util.List;

@Repository
@Transactional
public class GradeDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Grade grade) {
        em.persist(grade);
    }

    public List<Grade> getAll() {
        return em.createQuery("SELECT g FROM Grade g", Grade.class).getResultList();
    }
}
