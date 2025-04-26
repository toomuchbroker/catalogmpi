import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { InfoBarComponent } from "../info-bar/info-bar.component";

@Component({
  selector: 'app-user-panel',
  standalone: true,
  imports: [CommonModule, InfoBarComponent],
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent implements OnInit {
  user: any;
  userId: number = 0;
  userName: string = 'User';
  activeTab = 'assignments';
  enrollments: any[] = [];
  grades: any[] = [];
  assignments: any[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.userId = this.user.id;
      this.userName = this.user.name;
      this.fetchEnrollments();
      this.fetchGrades();
      this.fetchAssignments();
    }
  }

  fetchEnrollments() {
    this.http.get<any[]>(`http://localhost:8080/api/student/${this.userId}/enrollments`)
      .subscribe(data => this.enrollments = data);

    console.log(this.enrollments);
    console.log(this.userId);
  }

  fetchGrades() {
    this.http.get<any[]>(`http://localhost:8080/api/student/${this.userId}/grades-detailed`)
      .subscribe(data => this.grades = data);
  }

  fetchAssignments() {
    this.http.get<any[]>(`http://localhost:8080/api/student/${this.userId}/assignments`)
      .subscribe(data => this.assignments = data);
  }
}
