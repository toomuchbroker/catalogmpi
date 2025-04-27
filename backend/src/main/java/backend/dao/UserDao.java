package backend.dao;

import backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User findByEmailAndPassword(String email, String password) {
        List<User> result = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Transactional
    public void deleteById(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    @Transactional
    public void insert(User user) {
        em.persist(user);
        em.flush();
        em.refresh(user);
    }

    @Transactional
    public User findById(int id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User findByEmail(String email) {
        List<User> result = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Transactional
    public List<User> getAll() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        for (User user : users) {
            int userId = user.getId();
            if (!em.createNativeQuery("SELECT * FROM administrators WHERE user_id = :id")
                    .setParameter("id", userId)
                    .getResultList().isEmpty()) {
                user.setRole("admin");
            } else if (!em.createNativeQuery("SELECT * FROM teachers WHERE user_id = :id")
                    .setParameter("id", userId)
                    .getResultList().isEmpty()) {
                user.setRole("teacher");
            } else if (!em.createNativeQuery("SELECT * FROM students WHERE user_id = :id")
                    .setParameter("id", userId)
                    .getResultList().isEmpty()) {
                user.setRole("student");
            } else {
                user.setRole("unknown");
            }
        }

        return users;
    }

    @Transactional
    public void assignRole(int userId, String role) {
        em.createNativeQuery("DELETE FROM administrators WHERE user_id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        em.createNativeQuery("DELETE FROM teachers WHERE user_id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        em.createNativeQuery("DELETE FROM students WHERE user_id = :id")
                .setParameter("id", userId)
                .executeUpdate();

        switch (role.toLowerCase()) {
            case "admin":
                em.createNativeQuery("INSERT INTO administrators (user_id) VALUES (:id)")
                        .setParameter("id", userId)
                        .executeUpdate();
                logger.info("Assigned ADMIN role to user ID {}", userId);
                break;
            case "teacher":
                em.createNativeQuery("INSERT INTO teachers (user_id) VALUES (:id)")
                        .setParameter("id", userId)
                        .executeUpdate();
                logger.info("Assigned TEACHER role to user ID {}", userId);
                break;
            case "student":
                em.createNativeQuery("INSERT INTO students (user_id) VALUES (:id)")
                        .setParameter("id", userId)
                        .executeUpdate();
                logger.info("Assigned STUDENT role to user ID {}", userId);
                break;
            default:
                logger.warn("Attempted to assign unknown role '{}' to user ID {}", role, userId);
        }
    }
}
