package dao;

import jakarta.persistence.EntityManager;
import model.Course;
import model.Student;

import java.util.List;

public class CourseDao {
    private EntityManager em;

    public CourseDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Course course) {
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
    }

    public Course findById(int id) {
        return em.find(Course.class, id);
    }

    public List<Course> getAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }
}
