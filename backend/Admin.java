package backend; 

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    // Listă “globală” de utilizatori (exemplu didactic)
    // Într-un proiect real, datele ar fi în BD sau gestionate de un “service” separat
    private static List<User> allUsers = new ArrayList<>();

    // Constructor fără parametri
    public Admin() {
        super();
    }

    // Constructor cu parametri
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    /**
     * Creează un utilizator nou și îl adaugă în lista globală
     * @param user obiectul User (Student, Teacher sau Admin)
     */
    public void createUser(User user) {
        if (user != null && !allUsers.contains(user)) {
            allUsers.add(user);
            System.out.println("User " + user.getName() + " a fost creat cu succes!");
        }
    }

    /**
     * Șterge un utilizator din lista globală
     * @param user obiectul User ce urmează să fie șters
     */
    public void deleteUser(User user) {
        if (allUsers.remove(user)) {
            System.out.println("User " + user.getName() + " a fost șters!");
        } else {
            System.out.println("Userul nu a fost găsit în listă!");
        }
    }

    /**
     * Resetează parola unui utilizator
     * @param user obiectul User
     * @param newPassword noua parolă
     */
    public void resetPassword(User user, String newPassword) {
        if (user != null && allUsers.contains(user)) {
            user.setPassword(newPassword);
            System.out.println("Parola pentru " + user.getName() + " a fost resetată!");
        } else {
            System.out.println("Userul nu există în sistem!");
        }
    }

    /**
     * Metodă prin care Adminul poate asocia un profesor la un curs.
     * IMPORTANT: Necesită ca obiectul Course să aibă un atribut Teacher
     * și metoda setTeacher(Teacher teacher).
     *
     * @param teacher Obiectul Teacher
     * @param course  Obiectul Course
     */
    public void assignTeacherToCourse(Teacher teacher, Course course) {
        if (teacher == null || course == null) {
            System.out.println("Parametri invalizi!");
            return;
        }
        // Exemplu minimal: setăm profesorul cursului
        course.setTeacher(teacher);
        System.out.println("Profesorul " + teacher.getName()
            + " a fost asignat la cursul " + course.getName());
    }

    // Metodă auxiliară pentru a vedea toți userii din sistem (nu era în UML, dar ajută la debugging)
    public void listAllUsers() {
        System.out.println("===== Lista de Useri în sistem =====");
        for (User u : allUsers) {
            System.out.println("ID: " + u.getUserId() + " | Nume: " + u.getName() + " | Email: " + u.getEmail());
        }
    }
}