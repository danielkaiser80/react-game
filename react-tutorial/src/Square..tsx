import React from "react";

interface SquareProps {
    value: number;
}

interface SquareState {
    value: string;
}

export class Square extends React.Component<SquareProps, SquareState> {

    constructor(props: SquareProps) {
        super(props);
        this.state = {
            value: ''
        };
    }

    render() {
        return (
            <button className="square" onClick={() => this.setState({value: 'X'})}>
                {this.state.value}
            </button>
        );
    }
}