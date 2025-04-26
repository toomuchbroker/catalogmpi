package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.model.Course;

import java.util.List;

@Repository
@Transactional
public class CourseDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Course course) {
        em.persist(course);
    }

    public Course findById(int id) {
        return em.find(Course.class, id);
    }

    public List<Course> getAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    public void enrollStudent(int userId, int courseId) {

        Integer studentId = (Integer) em
                .createNativeQuery("SELECT id FROM students WHERE user_id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();

        System.out.println(studentId);

        em.createNativeQuery("INSERT INTO enrollments (student_id, course_id) VALUES (:studentId, :courseId)")
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }

    public void unenrollStudent(int userId, int courseId) {

        Integer studentId = (Integer) em
                .createNativeQuery("SELECT id FROM students WHERE user_id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();

        em.createNativeQuery("DELETE FROM enrollments WHERE course_id = :courseId AND student_id = :studentId")
                .setParameter("courseId", courseId)
                .setParameter("studentId", studentId)
                .executeUpdate();
    }

}
