import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-new-game-dialog',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './new-game-dialog.component.html',
  styleUrl: './new-game-dialog.component.scss',
})
export class NewGameDialogComponent {}
