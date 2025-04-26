import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

// Define an interface for Assignment matching your backend model.
export interface Assignment {
  id: number;
  title: string;
  description: string;
  deadline: string; // Use ISO formatted date string for simplicity.
  // Optionally, add a course property if needed.
}

@Component({
  selector: 'app-user-assignments',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './user-assignments.component.html',
  styleUrls: ['./user-assignments.component.css']
})
export class UserAssignmentsComponent implements OnInit {
  assignments: Assignment[] = [];
  errorMessage: string = '';

  // URL of your backend endpoint for assignments.
  private assignmentsUrl = 'http://localhost:8080/api/assignments';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAssignments();
  }

  loadAssignments(): void {
    this.http.get<Assignment[]>(this.assignmentsUrl).subscribe({
      next: (data) => {
        this.assignments = data;
      },
      error: (err) => {
        console.error('Error fetching assignments', err);
        this.errorMessage = 'Failed to load assignments.';
      }
    });
  }
}
