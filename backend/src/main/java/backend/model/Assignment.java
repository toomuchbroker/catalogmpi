package backend.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    // Maps the dueDate field to the "due_date" column in the database.
    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Default constructor required by JPA
    public Assignment() {
    }

    // Parameterized constructor for easy instantiation
    public Assignment(String title, String description, Date dueDate, Course course) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // Getters and setters. Consider renaming these to getDueDate() and setDueDate()
    // for consistency.
    public Date getDeadline() {
        return dueDate;
    }

    public void setDeadline(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
