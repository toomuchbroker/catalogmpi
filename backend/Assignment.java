package backend; 

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Assignment {

    private int assignmentId;
    private String title;
    private String description;
    private LocalDate dueDate;

    // Exemplu: un map pentru submisii (student => conținut)
    private Map<Student, String> submissions;

    // Constructor fără parametri
    public Assignment() {
        this.submissions = new HashMap<>();
    }

    // Constructor cu parametri
    public Assignment(int assignmentId, String title, String description, LocalDate dueDate) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.submissions = new HashMap<>();
    }

    // Getters și Setters

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Metodă prin care un student “trimite” tema.
     * @param student Obiectul Student care trimite tema
     * @param submissionContent Un text (sau o locație de fișier, link, etc.)
     */
    public void submitAssignment(Student student, String submissionContent) {
        if (student == null || submissionContent == null) {
            System.out.println("Parametrii invalizi la submitAssignment!");
            return;
        }

        // Opțional: poți verifica dacă e înainte de dueDate
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            System.out.println("Termenul a expirat! Studentul " + student.getName()
                + " nu mai poate trimite tema \"" + title + "\".");
            return;
        }

        // Salvăm “fișierul” sau conținutul în map
        submissions.put(student, submissionContent);
        System.out.println("Studentul " + student.getName()
            + " a trimis tema \"" + title + "\" cu succes!");
    }

    /**
     * Returnează toate submisiile pentru această temă.
     * @return Map cu (Student -> conținutul trimis)
     */
    public Map<Student, String> getSubmissions() {
        return submissions;
    }

    /**
     * Exemplu: să verifici ce a trimis un anume student
     */
    public String getSubmissionForStudent(Student student) {
        return submissions.get(student);
    }
}