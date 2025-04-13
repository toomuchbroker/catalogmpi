package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager em;

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
