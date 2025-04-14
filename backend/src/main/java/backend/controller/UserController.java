package backend.controller;

import backend.dao.UserDao;
import backend.model.User;
import backend.model.LoginRequest;
import backend.model.LoginResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userDao.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userDao.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userDao.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        int userId = user.getId();
        String role = "uscer";
        boolean isAdmin = !entityManager
                .createNativeQuery("SELECT * FROM administrators WHERE user_id = :id")
                .setParameter("id", userId)
                .getResultList()
                .isEmpty();

        boolean isTeacher = !entityManager
                .createNativeQuery("SELECT * FROM teachers WHERE user_id = :id")
                .setParameter("id", userId)
                .getResultList()
                .isEmpty();

        boolean isStudent = !entityManager
                .createNativeQuery("SELECT * FROM students WHERE user_id = :id")
                .setParameter("id", userId)
                .getResultList()
                .isEmpty();

        if (isAdmin) {
            role = "admin";
        } else if (isTeacher) {
            role = "teacher";
        } else if (isStudent) {
            role = "student";
        }

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                role);

        return ResponseEntity.ok(response);
    }
}
