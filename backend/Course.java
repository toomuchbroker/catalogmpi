package backend;


import java.util.ArrayList;
import java.util.List;

public class Course {

    // Atribute conform UML
    private int courseId;
    private String name;
    private String description;
    
    // Lista de studenți înscriși (poți adapta dacă vrei altă structură)
    private List<Student> enrolledStudents;
    
    // Constructor fără parametri (opțional)
    public Course() {
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Constructor cu parametri
    public Course(int courseId, String name, String description) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.enrolledStudents = new ArrayList<>();
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
    
    // Getters și setters
    
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
    
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
    
    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
