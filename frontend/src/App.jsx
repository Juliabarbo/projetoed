import { Routes, Route } from "react-router-dom";
import Cliente from "./pages/Cliente";
import Produto from "./pages/Produto";

export default function App() {
  return (
    <>
      <header><strong>Minha Loja</strong></header>
      <Routes>
        <Route path="/" element={<Cliente />} />
        <Route path="/produto/:id" element={<Produto />} />
      </Routes>
    </>
  );
}