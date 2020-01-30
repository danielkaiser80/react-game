import React from "react";

interface SquareProps {
    value: string;
    // FIXME set correct type
    onClick: any;
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
            <button className="square" onClick={() => this.props.onClick()}>
                {this.props.value}
            </button>
        );
    }
}