import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Board from "./Board";
import { SquareContent } from "./types";

test("clicking first button changes value to O", () => {
  let squareResults: SquareContent[] = [];
  const board = (
    <Board
      squares={["X", "X", "O", "X", "X", "O", null, "O", null]}
      xIsNext
      onPlay={(nextSquares) => {
        squareResults = nextSquares;
      }}
    />
  );
  render(board);

  const allButtons = screen.getAllByRole("button");
  fireEvent.click(allButtons[6]);

  expect(squareResults).toEqual(["X", "X", "O", "X", "X", "O", "X", "O", null]);
});
