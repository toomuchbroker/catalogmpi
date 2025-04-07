package dao;

import jakarta.persistence.EntityManager;
import model.Teacher;
import java.util.List;

public class TeacherDao {
    private EntityManager em;

    public TeacherDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Teacher teacher) {
        em.getTransaction().begin();
        em.persist(teacher);
        em.getTransaction().commit();
    }

    public List<Teacher> getAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }
}
