import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserTableComponent } from '../user-table/user-table.component';
import { AddUserComponent } from '../add-user/add-user.component';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, AddUserComponent, UserTableComponent],
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent { }
