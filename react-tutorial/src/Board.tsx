import React from "react";

interface BoardProps {
    squares: string[];
    onClick: (i: number) => void;
}

export class Board extends React.Component<BoardProps, {}> {

    renderSquare(i: number) {
        return <Square
            value={this.props.squares[i]}
            onClick={() => this.props.onClick(i)}
        />;
    }

    render() {
        return [0, 1, 2].map((i) => (
            <div className="board-row">
                {
                    [0, 1, 2].map(j => {
                        return this.renderSquare(i * 3 + j)
                    })
                }
            </div>
        ))
    }
}

interface SquareProps {
    value: string;
    onClick: () => void;
}

function Square(props: SquareProps) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}
