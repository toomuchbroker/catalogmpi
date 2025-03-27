package backend;


public class Grade {
    
    private int gradeId;
    private double value;
    private String subject;
    
    // Constructor fără parametri
    public Grade() {
    }
    
    // Constructor cu parametri
    public Grade(int gradeId, double value, String subject) {
        this.gradeId = gradeId;
        this.value = value;
        this.subject = subject;
    }

    // Getters și setters
    
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

