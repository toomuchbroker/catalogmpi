import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface User {
  id: number;
  name: string;
  email: string;
}

export interface Student {
  id: number;
  user: User;
}

export interface Assignment {
  id: number;
  title: string;
  description: string;
  deadline: string;
}

export interface Grade {
  id: number;
  value: number;
  dateAssigned: string;
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
  errorMessage = '';
  private gradesUrl = 'http://localhost:8080/api/grades';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadGrades();
  }

  loadGrades(): void {
    this.http.get<Grade[]>(this.gradesUrl).subscribe({
      next: (data) => (this.grades = data),
      error: (err) => {
        console.error('Error fetching grades', err);
        this.errorMessage = 'Failed to load grades.';
      }
    });
  }
}
