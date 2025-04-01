package backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa Course reprezintă un curs care are:
 * - un ID
 * - un nume și o descriere
 * - un Teacher asociat (opțional, dacă încă nu a fost setat)
 * - o listă de Assignment-uri
 * - o listă de Studenți înscriși
 */
public class Course {

    // Atribute conform UML + extensii pentru Teacher/Assignment
    private int courseId;
    private String name;
    private String description;

    private Teacher teacher;                  // Profesorul care predă cursul
    private List<Assignment> assignments;     // Lista de teme/proiecte
    private List<Student> enrolledStudents;   // Lista de studenți înscriși la curs

    // Constructor fără parametri
    public Course() {
        this.enrolledStudents = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    // Constructor cu parametri, inclusiv Teacher dacă vrei să-l setezi direct
    public Course(int courseId, String name, String description, Teacher teacher) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.teacher = teacher;  // poate fi null dacă nu există încă un profesor asociat
        this.enrolledStudents = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    /**
     * Adaugă un student la curs
     * @param student obiectul Student care urmează să fie înscris
     */
    public void addStudent(Student student) {
        if (student != null && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    /**
     * Șterge un student din curs
     * @param student obiectul Student care urmează să fie eliminat
     */
    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    /**
     * Adaugă un Assignment (temă) la acest curs
     * @param assignment obiectul Assignment
     */
    public void addAssignment(Assignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
        }
    }

    /**
     * Elimină un Assignment din acest curs
     * @param assignment obiectul Assignment de eliminat
     */
    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    // Getters și Setters

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Setează profesorul (Teacher) pentru acest curs
     * @param teacher obiectul Teacher
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
