import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { formatarPreco } from "../produtos";

const API = "http://localhost:8080/api";

export default function Produto() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [produto, setProduto] = useState(null);
  const [relacionados, setRelacionados] = useState([]);

  useEffect(() => {
    fetch(`${API}/produtos/${id}`).then(r => r.ok ? r.json() : null).then(setProduto);
    fetch(`${API}/produtos/${id}/relacionados`).then(r => r.json()).then(setRelacionados);
    fetch(`${API}/historico/${id}`, { method: "POST" });              // pilha: push
  }, [id]);

  const voltar = async () => {
    const { anterior } = await fetch(`${API}/historico/voltar`, { method: "POST" }).then(r => r.json());
    navigate(anterior ? `/produto/${anterior.id}` : "/");              // pilha: pop
  };

  const comprar = async () => {
    const { posicao } = await fetch(`${API}/pedidos/${id}`, { method: "POST" }).then(r => r.json());
    alert(`Pedido adicionado! Posição na fila: ${posicao}`);           // fila: enqueue
  };

  if (!produto) return <div className="container"><p>Produto não encontrado.</p></div>;

  return (
    <div className="container">
      <button className="voltar" onClick={voltar}>&larr; Voltar</button>
      <div className="card-detalhe">
        <div className="imagem"><img src={produto.imagem} alt={produto.nome} /></div>
        <div className="info">
          <h1>{produto.nome}</h1>
          <p>{produto.descricao}</p>
          <div className="preco grande">{formatarPreco(produto.preco)}</div>
          <button className="comprar" onClick={comprar}>Adicionar ao carrinho</button>
        </div>
      </div>

      <h3 style={{ margin: "24px 0 12px" }}>Produtos relacionados</h3>
      <div className="grid">
        {relacionados.map(r => (
          <div key={r.id} className="card-produto" onClick={() => navigate(`/produto/${r.id}`)} style={{ cursor: "pointer" }}>
            <img src={r.imagem} alt={r.nome} />
            <div className="corpo"><h3>{r.nome}</h3><div className="preco">{formatarPreco(r.preco)}</div></div>
          </div>
        ))}
      </div>
    </div>
  );
}