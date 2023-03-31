import React from "react";
import { SquareContent } from "./types";

interface SquareProps {
  value: SquareContent;
  onClick: () => void;
}

const Square = ({ onClick, value }: SquareProps) => (
  <button className="square" onClick={onClick} type="button">
    {value}
  </button>
);

export default Square;
