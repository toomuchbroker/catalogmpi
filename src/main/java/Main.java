import dao.*;
import jakarta.persistence.*;
import model.*;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n========== MENIU ==========");
            System.out.println("1. Adaugă un utilizator");
            System.out.println("2. Adaugă un student");
            System.out.println("3. Adaugă un profesor");
            System.out.println("4. Adaugă un curs");
            System.out.println("5. Adaugă o temă");
            System.out.println("6. Adaugă o notă");
            System.out.println("7. Afișează toți utilizatorii");
            System.out.println("8. Afișează toți studenții");
            System.out.println("9. Afișează toate cursurile");
            System.out.println("10. Afișează toate temele");
            System.out.println("11. Afișează toate notele");
            System.out.println("0. Ieșire");
            System.out.print("Alege o opțiune: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumă newline-ul

            switch (option) {
                case 1:
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Parolă: ");
                    String parola = scanner.nextLine();
                    userDao.insert(new User(nume, email, parola));
                    System.out.println("Utilizator adăugat.");
                    break;

                case 2:
                    System.out.print("ID utilizator student: ");
                    int uid = scanner.nextInt();
                    User studentUser = userDao.findById(uid);
                    if (studentUser != null) {
                        studentDao.insert(new Student(studentUser));
                        System.out.println("Student adăugat.");
                    } else {
                        System.out.println("Utilizator inexistent.");
                    }
                    break;

                case 3:
                    System.out.print("ID utilizator profesor: ");
                    int tid = scanner.nextInt();
                    User profUser = userDao.findById(tid);
                    if (profUser != null) {
                        teacherDao.insert(new Teacher(profUser));
                        System.out.println("Profesor adăugat.");
                    } else {
                        System.out.println("Utilizator inexistent.");
                    }
                    break;

                case 4:
                    System.out.print("Nume curs: ");
                    String numeCurs = scanner.nextLine();
                    System.out.print("Descriere curs: ");
                    String desc = scanner.nextLine();
                    System.out.print("ID profesor: ");
                    int profId = scanner.nextInt();
                    Teacher teacher = teacherDao.findById(profId);
                    if (teacher != null) {
                        courseDao.insert(new Course(numeCurs, desc, teacher));
                        System.out.println("Curs adăugat.");
                    } else {
                        System.out.println("Profesor inexistent.");
                    }
                    break;

                case 5:
                    System.out.print("Titlu temă: ");
                    String titlu = scanner.nextLine();
                    System.out.print("Descriere: ");
                    String descriere = scanner.nextLine();
                    System.out.print("Deadline (yyyy-mm-dd): ");
                    String deadline = scanner.nextLine();
                    System.out.print("ID curs: ");
                    int courseId = scanner.nextInt();
                    Course course = courseDao.findById(courseId);
                    if (course != null) {
                        assignmentDao.insert(new Assignment(titlu, descriere, Date.valueOf(deadline), course));
                        System.out.println("Temă adăugată.");
                    } else {
                        System.out.println("Curs inexistent.");
                    }
                    break;

                case 6:
                    System.out.print("Notă: ");
                    double nota = scanner.nextDouble();
                    System.out.print("Data evaluării (yyyy-mm-dd): ");
                    String dataEvaluare = scanner.next();
                    System.out.print("ID student: ");
                    int studId = scanner.nextInt();
                    System.out.print("ID temă: ");
                    int temaId = scanner.nextInt();

                    Student student = studentDao.findById(studId);
                    Assignment tema = assignmentDao.findById(temaId);

                    if (student != null && tema != null) {
                        gradeDao.insert(new Grade(nota, Date.valueOf(dataEvaluare), student, tema));
                        System.out.println("Notă adăugată.");
                    } else {
                        System.out.println("Student sau temă inexistentă.");
                    }
                    break;

                case 7:
                    System.out.println("\n--- Utilizatori ---");
                    for (User u : userDao.getAll()) {
                        System.out.println(u.getId() + " - " + u.getName() + " | " + u.getEmail());
                    }
                    break;

                case 8:
                    System.out.println("\n--- Studenți ---");
                    for (Student s : studentDao.getAll()) {
                        System.out.println(s.getId() + " - " + s.getUser().getName());
                    }
                    break;

                case 9:
                    System.out.println("\n--- Cursuri ---");
                    for (Course c : courseDao.getAll()) {
                        System.out.println(c.getId() + " - " + c.getName() + ": " + c.getDescription());
                    }
                    break;

                case 10:
                    System.out.println("\n--- Temele ---");
                    for (Assignment a : assignmentDao.getAll()) {
                        System.out.println(a.getId() + " - " + a.getTitle() + " (deadline: " + a.getDeadline() + ")");
                    }
                    break;

                case 11:
                    System.out.println("\n--- Note ---");
                    for (Grade g : gradeDao.getAll()) {
                        System.out.println(g.getId() + " - " + g.getStudent().getUser().getName() +
                                ", " + g.getAssignment().getTitle() + ", nota: " + g.getValue());
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }

        scanner.close();
        em.close();
        emf.close();
        System.out.println("Aplicația s-a încheiat.");
    }
}
