import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

// Define an interface for the Student portion of the Grade
export interface User {
  id: number;
  name: string;
  email: string;
  // password omitted for security reasons
}

export interface Student {
  id: number;
  user: User;
}

// Define an interface for an Assignment (if available)
export interface Assignment {
  id: number;
  title: string;
  description: string;
  deadline: string; // ISO date string
}

// Define an interface for Grade based on your backend model
export interface Grade {
  id: number;
  value: number;
  dateAssigned: string; // ISO formatted date string
  student: Student;
  assignment?: Assignment;
}

@Component({
  selector: 'app-user-grades',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './user-grades.component.html',
  styleUrls: ['./user-grades.component.css']
})
export class UserGradesComponent implements OnInit {
  grades: Grade[] = [];
  errorMessage: string = '';

  // URL of your backend endpoint for grades
  private gradesUrl = 'http://localhost:8080/api/grades';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadGrades();
  }

  // Load grades from the backend
  loadGrades(): void {
    this.http.get<Grade[]>(this.gradesUrl).subscribe({
      next: (data) => this.grades = data,
      error: (err) => {
        console.error('Error fetching grades', err);
        this.errorMessage = 'Failed to load grades.';
      }
    });
  }
}
