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

  const handleClick = (i: number) => {
    if (calculateWinner(currentSquares) ?? currentSquares[i]) {
      return;
    }

    const nextSquares = currentSquares.slice();
    nextSquares[i] = xIsNext ? "X" : "O";

    setHistory([...history, nextSquares]);
    setXIsNext(!xIsNext);
  };

  function jumpTo(move: number) {
    // TODO
  }

  const moves = useMemo(() => {
    return history.map((step, move) => {
      const desc = move ? `Go to move #${move}` : "Go to game start";
      return (
        <li key={step.join()}>
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
        <Board squares={currentSquares} onClick={(i) => handleClick(i)} />
      </div>
      <div className="game-info">
        <div>{status}</div>
        <ol>{moves}</ol>
      </div>
    </div>
  );
};

export default Game;
