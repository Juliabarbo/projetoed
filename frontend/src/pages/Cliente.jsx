import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { PRODUTOS, formatarPreco } from "../produtos";

export default function Cliente() {
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/produtos").then(r => r.json()).then(setProdutos);
    setProdutos(PRODUTOS);
  }, []);

  return (
    <div className="container">
      <h2>Produtos</h2>
      <p className="sub">Escolha um produto para ver os detalhes.</p>

      <div className="grid">
        {produtos.map((p) => (
          <Link key={p.id} to={`/produto/${p.id}`} className="card-produto">
            <img src={p.imagem} alt={p.nome} />
            <div className="corpo">
              <h3>{p.nome}</h3>
              <div className="preco">{formatarPreco(p.preco)}</div>
              <div className="ver">Ver produto</div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}