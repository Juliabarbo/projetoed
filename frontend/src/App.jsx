import { Routes, Route } from "react-router-dom";
import Cliente from "./pages/Cliente";
import Produto from "./pages/Produto";
import Login from "./pages/Login";

export default function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cliente" element={<><header><strong>Minha Loja</strong></header><Cliente /></>} />
        <Route path="/produto/:id" element={<><header><strong>Minha Loja</strong></header><Produto /></>} />
      </Routes>
    </>
  );
}