package dao;

import jakarta.persistence.EntityManager;
import model.Student;
import java.util.List;

public class StudentDao {
    private EntityManager em;

    public StudentDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Student student) {
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
    }

    public Student findById(int id) {
        return em.find(Student.class, id);
    }

    public List<Student> getAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }
}
