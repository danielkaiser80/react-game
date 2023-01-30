import React, { useMemo, useState } from "react";
import Board from "./Board";
import { calculateWinner } from "./util";

const Game = () => {
  const [xIsNext, setXIsNext] = useState(true);
  const [history, setHistory] = useState([
    {
      squares: Array(9).fill(null),
    },
  ]);

  const handleClick = (i: number) => {
    const squares = current.squares.slice();

    if (calculateWinner(squares) ?? squares[i]) {
      return;
    }

    squares[i] = xIsNext ? "X" : "O";
    setXIsNext(!xIsNext);

    setHistory(
      history.concat([
        {
          squares,
        },
      ])
    );
  };

  function jumpTo(move: number) {
    // TODO
  }

  const moves = useMemo(() => {
    return history.map((step, move) => {
      const desc = move ? `Go to move #${move}` : "Go to game start";
      return (
        <li>
          <button onClick={() => jumpTo(move)}>{desc}</button>
        </li>
      );
    });
  }, [history]);

  const current = history[history.length - 1];
  const winner = calculateWinner(current.squares);

  const status = winner
    ? `Winner: ${winner}`
    : `Next player: ${xIsNext ? "X" : "O"}`;

  return (
    <div className="game">
      <div className="game-board">
        <Board squares={current.squares} onClick={(i) => handleClick(i)} />
      </div>
      <div className="game-info">
        <div>{status}</div>
        <ol>{moves}</ol>
      </div>
    </div>
  );
};

export default Game;
