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
  courses: any[] = [];

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
    this.fetchCourses();
  }

  fetchAssignments() {
    this.http.get<any[]>('http://localhost:8080/api/assignments').subscribe({
      next: data => {
        this.assignments = data;
      }
    });
  }

  fetchCourses() {
    this.http.get<any[]>('http://localhost:8080/api/courses').subscribe({
      next: data => {
        this.courses = data;
      }
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
      dueDate: this.formatDate(this.newAssignment.due_date),
      course: this.newAssignment.course_id
        ? { id: Number(this.newAssignment.course_id) }
        : null
    };

    const request = this.editingAssignmentId
      ? this.http.put(`http://localhost:8080/api/assignments/${this.editingAssignmentId}`, preparedAssignment, { responseType: 'text' })
      : this.http.post('http://localhost:8080/api/assignments', preparedAssignment, { responseType: 'text' });

    request.subscribe({
      next: () => {
        alert(this.editingAssignmentId ? '✅ Assignment updated!' : '✅ Assignment added!');
        this.resetForm();
        this.fetchAssignments();
      }
    });
  }

  editAssignment(a: any) {
    this.editingAssignmentId = a.id;
    this.newAssignment = {
      title: a.title,
      description: a.description,
      due_date: this.formatDate(a.dueDate),
      course_id: a.course?.id || ''
    };
  }

  deleteAssignment(id: number) {
    if (confirm('❗ Are you sure you want to delete this assignment?')) {
      this.http.delete(`http://localhost:8080/api/assignments/${id}`, { responseType: 'text' }).subscribe({
        next: () => {
          alert('🗑️ Assignment deleted!');
          this.fetchAssignments();
        }
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
