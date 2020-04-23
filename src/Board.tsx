import React from "react";

interface BoardProps {
    squares: string[];
    onClick: (i: number) => void;
}

/**
 * The actual game board.
 */
const Board = (props: BoardProps) => {

    const renderSquare = (i: number) => {
        return <Square key={i}
                       value={props.squares[i]}
                       onClick={() => props.onClick(i)}
        />;
    };

    const board = [0, 1, 2].map(i => (
        <div key={`row_${i}`} className="board-row">
            {
                [0, 1, 2].map(j => {
                    return renderSquare(i * 3 + j)
                })
            }
        </div>
    ));
    return (<> {board} </>) // https://github.com/microsoft/TypeScript/issues/33487
}

export default Board;

interface SquareProps {
    value: string;
    onClick: () => void;
}

const Square = (props: SquareProps) => {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}
