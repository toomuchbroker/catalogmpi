package backend.controller;

import backend.dao.UserDao;
import backend.model.LoginRequest;
import backend.model.LoginResponse;
import backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userDao.getAll();
        users.forEach(u -> logger.info("Fetched user: {} with role: {}", u.getEmail(), u.getRole()));
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        logger.info("Fetching user by ID: {}", id);
        User user = userDao.findById(id);
        if (user != null) {
            logger.info("User found: {}", user.getEmail());
            return ResponseEntity.ok(user);
        } else {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> userData) {
        try {
            String name = userData.get("name");
            String email = userData.get("email");
            String password = userData.get("password");
            String role = userData.get("role");

            logger.info("Attempting to create user: {} ({}) with role: {}", name, email, role);

            if (name == null || email == null || password == null) {
                logger.warn("Missing required fields for user creation.");
                return ResponseEntity.badRequest().body(null);
            }

            if (userDao.findByEmail(email) != null) {
                logger.warn("User already exists with email: {}", email);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);

            userDao.insert(user);
            logger.info("User created with ID: {}", user.getId());

            userDao.assignRole(user.getId(), role);
            logger.info("Assigned role {} to user ID {}", role, user.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody Map<String, String> userData) {
        logger.info("Updating user with ID: {}", id);
        User existingUser = userDao.findById(id);
        if (existingUser == null) {
            logger.warn("User not found for update: ID {}", id);
            return ResponseEntity.notFound().build();
        }

        if (userData.containsKey("name")) {
            existingUser.setName(userData.get("name"));
        }
        if (userData.containsKey("email")) {
            existingUser.setEmail(userData.get("email"));
        }
        if (userData.containsKey("password")) {
            existingUser.setPassword(userData.get("password"));
        }

        userDao.update(existingUser);

        if (userData.containsKey("role")) {
            String role = userData.get("role");
            userDao.assignRole(existingUser.getId(), role);
            existingUser.setRole(role);
            logger.info("Updated role for user {} to {}", existingUser.getEmail(), role);
        }

        logger.info("User updated successfully with ID: {}", id);
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        logger.info("Deleting user with ID: {}", id);
        User user = userDao.findById(id);
        if (user == null) {
            logger.warn("Delete failed: user ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed: user not found");
        }

        userDao.deleteById(id);
        logger.info("User deleted successfully with ID: {}", id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());

        User user = userDao.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user == null) {
            logger.warn("Failed login attempt for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        int userId = user.getId();
        String role = "user";

        boolean isAdmin = !entityManager.createNativeQuery("SELECT * FROM administrators WHERE user_id = :id")
                .setParameter("id", userId).getResultList().isEmpty();
        boolean isTeacher = !entityManager.createNativeQuery("SELECT * FROM teachers WHERE user_id = :id")
                .setParameter("id", userId).getResultList().isEmpty();
        boolean isStudent = !entityManager.createNativeQuery("SELECT * FROM students WHERE user_id = :id")
                .setParameter("id", userId).getResultList().isEmpty();

        if (isAdmin)
            role = "admin";
        else if (isTeacher)
            role = "teacher";
        else if (isStudent)
            role = "student";

        logger.info("Login successful for {}: role = {}", user.getEmail(), role);

        LoginResponse response = new LoginResponse(user.getId(), user.getName(), user.getEmail(), role);
        return ResponseEntity.ok(response);
    }
}
