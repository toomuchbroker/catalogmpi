package backend;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    
    // Aici stocăm notele, dacă vrei să le ții direct în obiectul Student
    private List<Grade> grades;
    
    // Constructor fără parametri
    public Student() {
        super(); 
        this.grades = new ArrayList<>();
    }
    
    // Constructor cu parametri (inclusiv atribute din User)
    public Student(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.grades = new ArrayList<>();
    }
    
    /**
     * Metodă pentru a vizualiza notele studentului
     * @return Listă cu obiecte de tip Grade
     */
    public List<Grade> viewGrades() {
        // Deocamdată returnăm direct lista internă.
        // Dacă logica ta presupune o altă sursă de date (bază de date, etc.),
        // modifică implementarea în consecință.
        return grades;
    }
    
    /**
     * Poți adăuga o metodă de setare/adăugare a notelor
     * (Ex.: atunci când profesorul îți introduce o notă)
     */
    public void addGrade(Grade grade) {
        if (grade != null) {
            grades.add(grade);
        }
    }
    
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
