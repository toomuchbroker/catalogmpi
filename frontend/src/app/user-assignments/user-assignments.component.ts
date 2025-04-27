import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface Assignment {
  id: number;
  title: string;
  description: string;
  deadline: string;
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
  errorMessage = '';
  private assignmentsUrl = 'http://localhost:8080/api/assignments';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadAssignments();
  }

  loadAssignments(): void {
    this.http.get<Assignment[]>(this.assignmentsUrl).subscribe({
      next: (data) => this.assignments = data,
      error: () => this.errorMessage = 'Failed to load assignments.'
    });
  }
}
