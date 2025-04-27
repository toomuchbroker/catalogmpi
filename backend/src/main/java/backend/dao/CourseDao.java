package backend.dao;

import backend.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CourseDao {

    private static final Logger logger = LoggerFactory.getLogger(CourseDao.class);

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

    public Course update(Course course) {
        return em.merge(course);
    }

    public void delete(int id) {
        Course course = em.find(Course.class, id);
        if (course != null) {
            em.remove(course);
        }
    }

    public void enrollStudent(int userId, int courseId) {
        Integer studentId = (Integer) em
                .createNativeQuery("SELECT id FROM students WHERE user_id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();

        em.createNativeQuery("INSERT INTO enrollments (student_id, course_id) VALUES (:studentId, :courseId)")
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .executeUpdate();

        logger.info("Student with id {} enrolled in course id {}", studentId, courseId);
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

        logger.info("Student with id {} unenrolled from course id {}", studentId, courseId);
    }
}
