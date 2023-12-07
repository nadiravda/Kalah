import { GameResult } from './game-result';

export interface GameState {
  board: number[];
  currentPlayer: number;
  gameResult: GameResult;
}
