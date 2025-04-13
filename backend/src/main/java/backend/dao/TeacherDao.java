package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.model.Teacher;

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
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }
}
