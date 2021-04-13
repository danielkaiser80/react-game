import React, {useState} from 'react'
import Board from "./Board"

const Game = () => {

    const calculateWinner = (squares: string[]) => {
        const lines = [
            [0, 1, 2],
            [3, 4, 5],
            [6, 7, 8],
            [0, 3, 6],
            [1, 4, 7],
            [2, 5, 8],
            [0, 4, 8],
            [2, 4, 6],
        ]

        for (let i = 0; i < lines.length; i++) {
            const [a, b, c] = lines[i];
            if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
                return squares[a];
            }
        }
        return null
    }


    const handleClick = (i: number) => {
        const squares = current.squares.slice()

        if (calculateWinner(squares) ?? squares[i]) {
            return;
        }

        squares[i] = xIsNext ? 'X' : 'O'
        setXIsNext(!xIsNext)

        setHistory(history.concat(
            [{
                squares,
            }]
        ))
    }


    const [xIsNext, setXIsNext] = useState(true)
    const [history, setHistory] = useState([{
        squares: Array(9).fill(null),
    }])

    const current = history[history.length - 1]
    const winner = calculateWinner(current.squares)

    const status = winner ? `Winner: ${winner}` : `Next player: ${xIsNext ? 'X' : 'O'}`

    return (
        <div className="game">
            <div className="game-board">
                <Board
                    squares={current.squares}
                    onClick={(i) => handleClick(i)}
                />
            </div>
            <div className="game-info">
                <div>{status}</div>
                <ol>{/* TODO */}</ol>
            </div>
        </div>
    )
}

export default Game
