import { Component, OnInit } from '@angular/core';
import { FieldComponent } from '../field/field.component';
import { CommonModule } from '@angular/common';
import { GameService } from '../game.service';
import { FieldType } from '../models/field-type.model';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { NewGameDialogComponent } from '../new-game-dialog/new-game-dialog.component';
import { WinnerDialogComponent } from '../winner-dialog/winner-dialog.component';
import { GameResult } from '../models/game-result';
@Component({
  selector: 'app-board',
  standalone: true,
  imports: [FieldComponent, CommonModule, MatDialogModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss',
})
export class BoardComponent implements OnInit {
  board: number[] = [];
  playerTurn: number = 1;
  playerOneHouse = 0;
  playerOneFields: number[] = [];
  playerTwoHouse = 0;
  playerTwoFields: number[] = [];
  fieldTypes = FieldType;
  isGameOver: boolean = false;

  constructor(private kalahService: GameService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.getGameState();
  }

  getGameState(): void {
    this.kalahService.getGameState().subscribe((gameState) => {
      this.board = gameState.board;
      this.playerTurn = gameState.currentPlayer;
      this.mapfileds();
      this.isGameOver = gameState.gameResult !== GameResult.GAME_IN_PROGRESS;
      if (gameState.gameResult !== GameResult.GAME_IN_PROGRESS) {
        this.openWinnerDialog(gameState.gameResult);
      }
    });
  }

  openNewGameDialog(): void {
    const dialogRef = this.dialog.open(NewGameDialogComponent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.kalahService.startNewGame().subscribe(() => {
          this.getGameState();
        });
      }
    });
  }

  openWinnerDialog(gameResult: GameResult): void {
    let winner: string;

    switch (gameResult) {
      case GameResult.PLAYER_ONE_WINS:
        winner = 'Player 1';
        break;
      case GameResult.PLAYER_TWO_WINS:
        winner = 'Player 2';
        break;
      case GameResult.TIE:
        winner = 'Tie';
        break;
      default:
        winner = 'Unknown';
    }

    this.dialog.open(WinnerDialogComponent, {
      width: '500px',
      data: { winner: winner },
    });
  }

  onClick(fieldIndex: number): void {
    this.kalahService.makeMove(fieldIndex).subscribe(() => {
      this.getGameState();
    });
  }

  mapfileds() {
    this.playerOneFields = this.board.slice(0, this.board.length / 2 - 1);
    this.playerTwoFields = this.board.slice(
      this.board.length / 2,
      this.board.length - 1
    );
    this.playerOneHouse = this.board[6];
    this.playerTwoHouse = this.board[this.board.length - 1];
  }
}
