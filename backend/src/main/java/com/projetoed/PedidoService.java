package com.projetoed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private final Map<Integer, List<Integer>> pedidos = new ConcurrentHashMap<>();

    public int adicionarPedido(Integer produtoId) {
        var fila = pedidos.computeIfAbsent(produtoId, chave -> new ArrayList<>());
        fila.add(produtoId);
        return fila.size();
    }
}
