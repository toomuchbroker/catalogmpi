import dao.*;
import jakarta.persistence.*;
import model.*;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogPU");
        EntityManager em = emf.createEntityManager();

        // DAOs
        UserDao userDao = new UserDao(em);
        TeacherDao teacherDao = new TeacherDao(em);
        StudentDao studentDao = new StudentDao(em);
        CourseDao courseDao = new CourseDao(em);
        AssignmentDao assignmentDao = new AssignmentDao(em);
        GradeDao gradeDao = new GradeDao(em);

        // 1. Adăugăm un profesor
        User profUser = new User("Livia Sângeorzan", "livia@univ.ro", "parola123");
        userDao.insert(profUser);

        Teacher teacher = new Teacher(profUser);
        teacherDao.insert(teacher);

        // 2. Cursul profesorului
        Course course = new Course("Fotografie Creativă", "Introducere în compoziție și lumină", teacher);
        courseDao.insert(course);

        // 3. Studentul
        User studentUser = new User("Roxi Brănescu", "roxi@student.ro", "pass123");
        userDao.insert(studentUser);

        Student student = new Student(studentUser);
        studentDao.insert(student);

        // 4. O temă pentru curs
        Assignment assignment = new Assignment(
                "Portret în lumină naturală",
                "Realizează un portret folosind doar lumină naturală.",
                Date.valueOf("2025-04-30"),
                course);
        assignmentDao.insert(assignment);

        // 5. Notăm tema studentului
        Grade grade = new Grade(9.75, Date.valueOf("2025-04-01"), student, assignment);
        gradeDao.insert(grade);

        // 6. Afișăm datele inserate
        System.out.println("\n--- Cursuri ---");
        for (Course c : courseDao.getAll()) {
            System.out.println("Curs: " + c.getName() + " - " + c.getDescription());
        }

        System.out.println("\n--- Studenți ---");
        for (Student s : studentDao.getAll()) {
            System.out.println("Student: " + s.getUser().getName() + " - " + s.getUser().getEmail());
        }

        System.out.println("\n--- Note ---");
        for (Grade g : gradeDao.getAll()) {
            System.out.println("Student: " + g.getStudent().getUser().getName() +
                    ", Tema: " + g.getAssignment().getTitle() +
                    ", Nota: " + g.getValue());
        }

        em.close();
        emf.close();
    }
}
