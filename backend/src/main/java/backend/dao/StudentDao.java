package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.model.Student;

import java.util.List;

@Repository
@Transactional
public class StudentDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Student student) {
        em.persist(student);
    }

    public Student findById(int id) {
        return em.find(Student.class, id);
    }

    public List<Student> getAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }
}
