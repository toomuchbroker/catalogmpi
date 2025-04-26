import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-assignments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-assignments.component.html',
  styleUrls: ['./admin-assignments.component.css']
})
export class AdminAssignmentsComponent implements OnInit {
  assignments: any[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchAssignments();
  }

  fetchAssignments() {
    this.http.get<any[]>('http://localhost:8080/api/assignments')
      .subscribe({
        next: data => {
          this.assignments = data;
          console.log('Assignments:', this.assignments); // 👈 logs when data is actually received
        },
        error: err => console.error('Error fetching assignments', err)
      });
  }

}
