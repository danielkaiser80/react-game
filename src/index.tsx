import "./index.css";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import Game from "./Game";

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
const root = createRoot(document.getElementById("root")!);
root.render(
  <StrictMode>
    <Game />
  </StrictMode>
);
