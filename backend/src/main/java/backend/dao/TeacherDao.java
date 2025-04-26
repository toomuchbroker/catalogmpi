package backend.dao;

import backend.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;      
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TeacherDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Teacher teacher) {
        em.persist(teacher);
    }

    public Teacher findById(int id) {
        return em.find(Teacher.class, id);
    }

    public List<Teacher> getAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class)
                 .getResultList();
    }

    public Teacher findByUserId(Integer userId) {
        try {
            return em.createQuery(
                    "SELECT t FROM Teacher t WHERE t.user.id = :uid",
                    Teacher.class)
                     .setParameter("uid", userId)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
