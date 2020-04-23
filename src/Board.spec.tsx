import React from 'react'
import {fireEvent, render, screen} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Board from "./Board";


test('clicking first button changes value to O', async () => {

    const board = <Board squares={["X", "X", "O", "X", "X", "O", "", "O", ""]} onClick={(i) => handleClick(i)}/>;
    render(board)

    function handleClick(i: number) {
        allButtons[i].textContent = "O";
    }

    const allButtons = screen.getAllByRole('button');
    fireEvent.click(allButtons[0])

    const buttonTexts = allButtons.map(value => value.textContent);
    expect(buttonTexts).toEqual(["O", "X", "O", "X", "X", "O", "", "O", ""])
    expect(allButtons[0]).toHaveTextContent("O")
})
