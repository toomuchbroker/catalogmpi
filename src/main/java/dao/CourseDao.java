package dao;

import jakarta.persistence.EntityManager;
import model.Course;
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

    public List<Course> getAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }
}
