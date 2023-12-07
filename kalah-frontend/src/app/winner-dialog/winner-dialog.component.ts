import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-winner-dialog',
  standalone: true,
  imports: [MatDialogModule, CommonModule],
  templateUrl: './winner-dialog.component.html',
  styleUrl: './winner-dialog.component.scss',
})
export class WinnerDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
