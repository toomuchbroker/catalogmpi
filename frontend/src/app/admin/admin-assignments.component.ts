import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-assignments',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-assignments.component.html',
  styleUrls: ['./admin-assignments.component.css']
})
export class AdminAssignmentsComponent implements OnInit {
  assignments: any[] = [];
  newAssignment = {
    title: '',
    description: '',
    due_date: '',
    course_id: ''
  };
  editingAssignmentId: number | null = null;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchAssignments();
  }

  fetchAssignments() {
    this.http.get<any[]>('http://localhost:8080/api/assignments').subscribe({
      next: data => {
        this.assignments = data;
        console.log('Assignments:', this.assignments);
      },
      error: err => console.error('Error fetching assignments', err)
    });
  }

  formatDate(date: any): string {
    if (!date) return '';
    const d = new Date(date);
    const year = d.getFullYear();
    const month = (d.getMonth() + 1).toString().padStart(2, '0');
    const day = d.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  saveAssignment() {
    const preparedAssignment = {
      title: this.newAssignment.title,
      description: this.newAssignment.description,
      dueDate: this.formatDate(this.newAssignment.due_date), // CAMELCASE
      course: this.newAssignment.course_id
        ? { id: Number(this.newAssignment.course_id) } // dacă ai ales un curs
        : null // dacă nu ai ales nimic
    };

    const request = this.editingAssignmentId
      ? this.http.put(`http://localhost:8080/api/assignments/${this.editingAssignmentId}`, preparedAssignment, { responseType: 'text' })
      : this.http.post('http://localhost:8080/api/assignments', preparedAssignment, { responseType: 'text' });

    request.subscribe({
      next: () => {
        alert(this.editingAssignmentId ? '✅ Assignment updated!' : '✅ Assignment added!');
        this.resetForm();
        this.fetchAssignments();
        console.log(preparedAssignment);
      },
      error: err => console.error('Error saving assignment', err)
    });
  }

  editAssignment(a: any) {
    this.editingAssignmentId = a.id;
    this.newAssignment = {
      title: a.title,
      description: a.description,
      due_date: this.formatDate(a.due_date),
      course_id: a.course?.id || ''
    };
  }

  deleteAssignment(id: number) {
    if (confirm('❗ Are you sure you want to delete this assignment?')) {
      this.http.delete(`http://localhost:8080/api/assignments/${id}`, { responseType: 'text' }).subscribe({
        next: () => {
          alert('🗑️ Assignment deleted!');
          this.fetchAssignments();
        },
        error: err => console.error('Error deleting assignment', err)
      });
    }
  }

  resetForm() {
    this.newAssignment = {
      title: '',
      description: '',
      due_date: '',
      course_id: ''
    };
    this.editingAssignmentId = null;
  }
}
