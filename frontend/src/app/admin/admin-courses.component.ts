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
  teachers: any[] = [];
  selectedStudent: { [courseId: number]: number | undefined } = {};
  newCourse = { name: '', description: '', teacherId: null as number | null };
  edit: { [id: number]: { name: string; description: string } } = {};

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchCourses();
    this.fetchUsers();
    this.fetchTeachers();
  }

  fetchCourses() {
    this.http.get<any[]>('http://localhost:8080/api/courses').subscribe({
      next: data => (this.courses = data)
    });
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/api/users').subscribe({
      next: data => {
        this.users = data;
        this.students = data.filter(u => u.role === 'student');
      }
    });
  }

  fetchTeachers() {
    this.http.get<any[]>('http://localhost:8080/api/teachers').subscribe({
      next: data => (this.teachers = data)
    });
  }

  createCourse() {
    const { name, description, teacherId } = this.newCourse;
    if (!name || !teacherId) return;

    this.http.post('http://localhost:8080/api/courses', {
      name,
      description,
      teacher: { id: teacherId }
    }).subscribe({
      next: () => {
        this.newCourse = { name: '', description: '', teacherId: null };
        this.fetchCourses();
      }
    });
  }

  startEdit(c: any) {
    this.edit[c.id] = { name: c.name, description: c.description };
  }

  cancelEdit(id: number) {
    delete this.edit[id];
  }

  saveEdit(c: any) {
    const draft = this.edit[c.id];
    this.http.put(`http://localhost:8080/api/courses/${c.id}`, {
      name: draft.name,
      description: draft.description,
      teacher: { id: c.teacher.id }
    }).subscribe({
      next: () => {
        delete this.edit[c.id];
        this.fetchCourses();
      }
    });
  }

  deleteCourse(id: number) {
    if (!confirm('Delete this course?')) return;
    this.http.delete(`http://localhost:8080/api/courses/${id}`).subscribe({
      next: () => this.fetchCourses()
    });
  }

  assignStudentToCourse(courseId: number) {
    const studentId = this.selectedStudent[courseId];
    if (!studentId) return;

    this.http.post('http://localhost:8080/api/courses/enroll',
      { studentId, courseId },
      { responseType: 'text' }
    ).subscribe({
      next: () => alert(`Student assigned to course ${courseId}!`)
    });
  }

  unenrollStudentFromCourse(courseId: number) {
    const studentId = this.selectedStudent[courseId];
    if (!studentId) return;

    this.http.delete(
      `http://localhost:8080/api/courses/unenroll?courseId=${courseId}&studentId=${studentId}`,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        alert(`❌ Student removed from course ${courseId}!`);
        this.selectedStudent[courseId] = undefined;
      }
    });
  }
}
