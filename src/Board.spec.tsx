import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Board from "./Board";

test("clicking first button changes value to O", async () => {
  let allButtons: HTMLElement[];
  const board = (
    <Board
      squares={["X", "X", "O", "X", "X", "O", null, "O", null]}
      onClick={(i) => {
        allButtons[i].textContent = "O";
      }}
    />
  );
  render(board);

  allButtons = screen.getAllByRole("button");
  fireEvent.click(allButtons[0]);

  const buttonTexts = allButtons.map((value) => value.textContent);
  expect(buttonTexts).toEqual(["O", "X", "O", "X", "X", "O", "", "O", ""]);
  expect(allButtons[0]).toHaveTextContent("O");
});
