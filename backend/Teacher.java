package backend; // Înlocuiește cu pachetul tău

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa Teacher extinde User și reprezintă un profesor
 * care poate preda mai multe cursuri, poate crea teme
 * (Assignment) și nota studenții (adauga Grade).
 */
public class Teacher extends User {

    // Cursurile predate de acest profesor
    private List<Course> courses;

    // Constructor fără parametri
    public Teacher() {
        super();
        this.courses = new ArrayList<>();
    }

    // Constructor cu parametri
    public Teacher(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.courses = new ArrayList<>();
    }

    /**
     * Profesorul creează un assignment și îl atașează la curs.
     * @param course     Cursul căruia îi aparține assignment-ul
     * @param assignment Obiectul Assignment creat
     */
    public void createAssignment(Course course, Assignment assignment) {
        if (course == null || assignment == null) {
            System.out.println("createAssignment: Parametri invalizi!");
            return;
        }

        // Verificăm dacă acest profesor predă cursul respectiv
        if (!courses.contains(course)) {
            System.out.println("Profesorul " + getName() + " nu predă cursul " + course.getName());
            return;
        }

        // Adăugăm assignment-ul în curs
        course.addAssignment(assignment);
        System.out.println("Profesorul " + getName() 
            + " a creat assignment-ul \"" + assignment.getTitle() 
            + "\" pentru cursul " + course.getName());
    }

    /**
     * Profesorul notează un student, adăugând un obiect Grade în lista de note a studentului.
     * @param student Studentul care primește nota
     * @param grade   Obiectul Grade cu detaliile notei
     */
    public void gradeStudent(Student student, Grade grade) {
        if (student == null || grade == null) {
            System.out.println("gradeStudent: Parametri invalizi!");
            return;
        }
        student.addGrade(grade);
        System.out.println("Profesorul " + getName() + " i-a acordat studentului " 
            + student.getName() + " nota " + grade.getValue());
    }

    /**
     * Adaugă un curs în lista cursurilor predate de acest profesor.
     * De asemenea, setăm profesorul în obiectul Course (dacă logica ta impune acest lucru).
     * @param course Obiectul Course de adăugat
     */
    public void addCourse(Course course) {
        if (course == null) {
            System.out.println("addCourse: Curs invalid!");
            return;
        }
        if (!courses.contains(course)) {
            courses.add(course);
            course.setTeacher(this);  // dacă vrei să asociezi cursul și la profesor
            System.out.println("Profesorul " + getName() 
                + " predă acum cursul " + course.getName());
        } else {
            System.out.println("Profesorul " + getName() 
                + " deja predă cursul " + course.getName());
        }
    }

    /**
     * Elimină un curs din lista cursurilor predate de acest profesor.
     * @param course Cursul de eliminat
     */
    public void removeCourse(Course course) {
        if (course == null) {
            System.out.println("removeCourse: Curs invalid!");
            return;
        }
        if (courses.remove(course)) {
            // Opțional, poți șterge și referința la teacher în curs, dacă logica o cere
            course.setTeacher(null);
            System.out.println("Profesorul " + getName() 
                + " nu mai predă cursul " + course.getName());
        } else {
            System.out.println("Profesorul " + getName() 
                + " nu predă cursul " + course.getName() + " (nu a fost eliminat).");
        }
    }

    // Getter pentru lista de cursuri
    public List<Course> getCourses() {
        return courses;
    }

    // Setter pentru lista de cursuri
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Metodă auxiliară pentru a lista cursurile predate
     */
    public void listCourses() {
        System.out.println("===== Cursurile profesorului " + getName() + " =====");
        for (Course c : courses) {
            System.out.println("ID: " + c.getCourseId() + " | Nume: " + c.getName());
        }
    }
}