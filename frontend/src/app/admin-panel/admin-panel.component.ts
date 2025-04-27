import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserTableComponent } from '../user-table/user-table.component';
import { AddUserComponent } from '../add-user/add-user.component';
import { InfoBarComponent } from '../info-bar/info-bar.component';
import { AdminAssignmentsComponent } from '../admin/admin-assignments.component';
import { AdminCoursesComponent } from '../admin/admin-courses.component';
import { AdminGradesComponent } from '../admin/admin-grades.component';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, AddUserComponent, UserTableComponent, InfoBarComponent, AdminAssignmentsComponent, AdminCoursesComponent, AdminGradesComponent],
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent {
  activeTab: string = 'users';
}
