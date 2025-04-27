import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-grades',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-grades.component.html',
  styleUrls: ['./admin-grades.component.css']
})
export class AdminGradesComponent implements OnInit {
  grades: any[] = [];
  assignments: any[] = [];
  students: any[] = [];


  newGrade = {
    value: '',
    assignmentId: '',
    studentId: ''
  };
  editingGradeId: number | null = null;

  studentIdForAverage: number | null = null;
  studentAverage: number | null = null;


  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchGrades();
    this.fetchAssignments();
    this.fetchStudents();
  }


  fetchGrades() {
    this.http.get<any[]>('http://localhost:8080/api/grades').subscribe({
      next: data => this.grades = data,
      error: err => console.error('Error fetching grades', err)
    });
  }

  fetchAssignments() {
    this.http.get<any[]>('http://localhost:8080/api/assignments').subscribe({
      next: data => this.assignments = data,
      error: err => console.error('Error fetching assignments', err)
    });
  }

  fetchStudents() {
    this.http.get<any[]>('http://localhost:8080/api/users').subscribe({
      next: users => {
        this.students = users.filter(u => u.role === 'student');
        console.log(this.students);
      },
      error: err => console.error('Error fetching students', err)
    });
  }


  saveGrade() {
    const preparedGrade = {
      value: this.newGrade.value,
      assignmentId: this.newGrade.assignmentId,
      studentId: this.newGrade.studentId
    };

    console.log(preparedGrade);

    const request = this.editingGradeId
      ? this.http.put(`http://localhost:8080/api/grades/${this.editingGradeId}`, preparedGrade, { responseType: 'text' })
      : this.http.post('http://localhost:8080/api/grades', preparedGrade, { responseType: 'text' });

    request.subscribe({
      next: () => {
        alert(this.editingGradeId ? '✅ Grade updated!' : '✅ Grade added!');
        this.resetForm();
        this.fetchGrades();
      },
      error: err => console.error('Error saving grade', err)
    });
  }

  editGrade(grade: any) {
    this.editingGradeId = grade.id;
    this.newGrade = {
      value: grade.value,
      assignmentId: grade.assignment?.id || '',
      studentId: grade.student?.id || ''
    };
  }

  getStudentNameById(id: number | null): string {
    const student = this.students.find(s => s.id === id);
    return student ? student.user?.name : `ID ${id}`;
  }


  deleteGrade(id: number) {
    if (confirm('❗ Are you sure you want to delete this grade?')) {
      this.http.delete(`http://localhost:8080/api/grades/${id}`, { responseType: 'text' }).subscribe({
        next: () => {
          alert('🗑️ Grade deleted!');
          this.fetchGrades();
        },
        error: err => console.error('Error deleting grade', err)
      });
    }
  }

  resetForm() {
    this.newGrade = {
      value: '',
      assignmentId: '',
      studentId: ''
    };
    this.editingGradeId = null;
  }

  getStudentAverage() {
    if (!this.studentIdForAverage) return;

    console.log(this.studentIdForAverage);

    this.http.get<number>(`http://localhost:8080/api/grades/average/${this.studentIdForAverage}`)
      .subscribe({
        next: avg => {
          this.studentAverage = avg;
          console.log(`Average for student ${this.studentIdForAverage}:`, avg);
        },
        error: err => {
          console.error('Error fetching average', err);
          this.studentAverage = null;
        }
      });
  }

}
