package backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.model.User;

import java.util.List;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public User findByEmailAndPassword(String email, String password) {
        List<User> result = em
                .createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public void insert(User user) {
        em.persist(user);
    }

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
