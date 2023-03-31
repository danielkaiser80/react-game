import React, { useMemo, useState } from "react";
import Board from "./Board";
import calculateWinner from "./util";
import { SquareContent } from "./types";

const Game = () => {
  const [xIsNext, setXIsNext] = useState(true);
  const [history, setHistory] = useState<SquareContent[][]>([
    Array(9).fill(null),
  ]);
  const currentSquares = history[history.length - 1];

  function handlePlay(nextSquares: SquareContent[]) {
    setHistory([...history, nextSquares]);
    setXIsNext(!xIsNext);
  }

  function jumpTo(move: number) {
    // TODO
  }

  const moves = useMemo(() => {
    return history.map((step, move) => {
      const desc = move ? `Go to move #${move}` : "Go to game start";
      return (
        // safe as no move is re-ordered or deleted
        // eslint-disable-next-line react/no-array-index-key
        <li key={move}>
          <button onClick={() => jumpTo(move)} type="button">
            {desc}
          </button>
        </li>
      );
    });
  }, [history]);

  const winner = calculateWinner(currentSquares);

  const status = winner
    ? `Winner: ${winner}`
    : `Next player: ${xIsNext ? "X" : "O"}`;

  return (
    <div className="game">
      <div className="game-board">
        <Board
          squares={currentSquares}
          onPlay={(nextSquares) => handlePlay(nextSquares)}
          xIsNext={xIsNext}
        />
      </div>
      <div className="game-info">
        <div>{status}</div>
        <ol>{moves}</ol>
      </div>
    </div>
  );
};

export default Game;
