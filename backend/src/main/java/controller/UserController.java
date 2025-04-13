package controller;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // Adjust the allowed origin as needed.
public class UserController {

    @Autowired
    private UserDao userDao;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userDao.findById(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Insert a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userDao.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
