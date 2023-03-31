import React from "react";
import Square from "./Square";
import { SquareContent } from "./types";
import calculateWinner from "./util";

interface BoardProps {
  squares: SquareContent[];
  xIsNext: boolean;
  onPlay: (nextSquares: SquareContent[]) => void;
}

/**
 * The actual game board.
 */
const Board = ({ onPlay, xIsNext, squares }: BoardProps) => {
  const handleClick = (i: number) => {
    if (calculateWinner(squares) ?? squares[i]) {
      return;
    }

    const nextSquares = squares.slice();
    nextSquares[i] = xIsNext ? "X" : "O";
    onPlay(nextSquares);
  };

  const renderSquare = (i: number) => (
    <Square key={i} value={squares[i]} onClick={() => handleClick(i)} />
  );

  return (
    <>
      {" "}
      {[0, 1, 2].map((i) => (
        <div key={`row_${i}`} className="board-row">
          {[0, 1, 2].map((j) => renderSquare(i * 3 + j))}
        </div>
      ))}{" "}
    </>
  );
};

export default Board;
