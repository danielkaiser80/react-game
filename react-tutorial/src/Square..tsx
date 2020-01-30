import React from "react";

interface SquareProps {
    value: number;
}

export class Square extends React.Component<SquareProps, {}> {

    constructor(props: Readonly<SquareProps>) {
        super(props);
        this.state = {
            value: null,
        };
    }

    render() {
        return (
            <button className="square" onClick={() => alert('click')}>
                {this.props.value}
            </button>
        );
    }
}