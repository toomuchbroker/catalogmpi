import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-courses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-courses.component.html',
  styleUrls: ['./admin-courses.component.css']
})
export class AdminCoursesComponent implements OnInit {
  courses: any[] = [];
  users: any[] = [];
  students: any[] = [];
  selectedStudent: { [courseId: number]: number | undefined } = {};

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchCourses();
    this.fetchUsers();
  }

  fetchCourses() {
    this.http.get<any[]>('http://localhost:8080/api/courses').subscribe({
      next: data => this.courses = data,
      error: err => console.error('Error fetching courses', err)
    });
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/api/users').subscribe({
      next: data => {
        this.users = data;
        this.students = data.filter(u => u.role === 'student');
      },
      error: err => console.error('Error fetching users', err)
    });
  }

  assignStudentToCourse(courseId: number) {
    const studentId = this.selectedStudent[courseId];
    if (!studentId) return;

    this.http.post('http://localhost:8080/api/courses/enroll', {
      studentId,
      courseId
    }, { responseType: 'text' }).subscribe({
      next: () => alert(`Student assigned to course ${courseId}!`),
      error: err => console.error('Error assigning student:', err)
    });
  }

  unenrollStudentFromCourse(courseId: number) {
    const studentId = this.selectedStudent[courseId];
    if (!studentId) return;

    this.http.delete(`http://localhost:8080/api/courses/unenroll?courseId=${courseId}&studentId=${studentId}`, {
      responseType: 'text'
    }).subscribe({
      next: () => {
        alert(`❌ Student removed from course ${courseId}!`);
        this.selectedStudent[courseId] = undefined; // Optional: clear selection
      },
      error: err => console.error('Error unenrolling student:', err)
    });
  }
}
