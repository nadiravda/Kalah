import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GameState } from './models/game-state';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  makeMove(fieldIndex: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/move`, { fieldIndex });
  }

  getGameState(): Observable<GameState> {
    return this.http.get<GameState>(`${this.apiUrl}/game-state`);
  }

  startNewGame(): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/new-game`);
  }
}
