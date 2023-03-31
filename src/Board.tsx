import React from "react";
import Square from "./Square";
import { SquareContent } from "./types";

interface BoardProps {
  squares: SquareContent[];
  onClick: (i: number) => void;
}

/**
 * The actual game board.
 */
const Board = ({ onClick, squares }: BoardProps) => {
  const renderSquare = (i: number) => (
    <Square key={i} value={squares[i]} onClick={() => onClick(i)} />
  );

  const board = [0, 1, 2].map((i) => (
    <div key={`row_${i}`} className="board-row">
      {[0, 1, 2].map((j) => renderSquare(i * 3 + j))}
    </div>
  ));

  return <> {board} </>; // https://github.com/microsoft/TypeScript/issues/33487
};

export default Board;
