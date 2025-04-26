import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface Course {
  id: number;
  name: string;
  description: string;
}

@Component({
  selector: 'app-user-courses',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './user-courses.component.html',
  styleUrls: ['./user-courses.component.css']
})
export class UserCoursesComponent implements OnInit {
  courses: Course[] = [];
  errorMessage: string = '';
  private coursesUrl = 'http://localhost:8080/api/courses';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<Course[]>(this.coursesUrl).subscribe({
      next: (data) => this.courses = data,
      error: (err) => {
        console.error('Error fetching courses', err);
        this.errorMessage = 'Failed to load courses.';
      }
    });
  }
}
