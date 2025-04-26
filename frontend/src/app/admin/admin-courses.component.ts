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

  /* ------------ DATA ------------ */

  courses: any[]   = [];
  users:   any[]   = [];
  students:any[]   = [];
  teachers:any[]   = [];                // <- teachers for “new course” dropdown

  selectedStudent: { [courseId: number]: number | undefined } = {};

  /* ------------ NEW-COURSE FORM ------------ */

  newCourse = { name: '', description: '', teacherId: null as number | null };

  /* ------------ EDIT CACHE ------------ */

  edit: { [id: number]: { name: string; description: string } } = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchCourses();
    this.fetchUsers();
    this.fetchTeachers();
  }

  /* ---------------------------- LOAD ---------------------------- */

  fetchCourses() {
    this.http.get<any[]>('http://localhost:8080/api/courses')
             .subscribe({
               next: data => (this.courses = data),
               error: err  => console.error('Error fetching courses', err)
             });
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/api/users')
             .subscribe({
               next: data => {
                 this.users    = data;
                 this.students = data.filter(u => u.role === 'student');
               },
               error: err => console.error('Error fetching users', err)
             });
  }

  fetchTeachers() {
    this.http.get<any[]>('http://localhost:8080/api/teachers')
             .subscribe({
               next: data => (this.teachers = data),
               error: err  => console.error('Error fetching teachers', err)
             });
  }

  /* ------------------------- COURSE CRUD ------------------------ */

  /** Create */
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
      },
      error: err => console.error('Error creating course', err)
    });
  }

  /** Enter edit mode */
  startEdit(c: any) {
    this.edit[c.id] = { name: c.name, description: c.description };
  }

  /** Cancel edit */
  cancelEdit(id: number) {
    delete this.edit[id];
  }

  /** Save edit */
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
      },
      error: err => console.error('Error updating course', err)
    });
  }

  /** Delete */
  deleteCourse(id: number) {
    if (!confirm('Delete this course?')) return;
    this.http.delete(`http://localhost:8080/api/courses/${id}`)
             .subscribe({
               next: () => this.fetchCourses(),
               error: err => console.error('Error deleting course', err)
             });
  }

  /* --------------------- ENROL / UNENROL ------------------------ */

  assignStudentToCourse(courseId: number) {
    const studentId = this.selectedStudent[courseId];
    if (!studentId) return;

    this.http.post('http://localhost:8080/api/courses/enroll',
      { studentId, courseId },
      { responseType: 'text' }
    ).subscribe({
      next: () => alert(`Student assigned to course ${courseId}!`),
      error: err => console.error('Error assigning student:', err)
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
      },
      error: err => console.error('Error unenrolling student:', err)
    });
  }
}
