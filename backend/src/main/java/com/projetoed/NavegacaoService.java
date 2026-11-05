package com.projetoed;

import java.util.ArrayDeque;
import java.util.Deque;
import org.springframework.stereotype.Service;

@Service
public class NavegacaoService {
    private final Deque<Integer> historico = new ArrayDeque<>();

    public void adicionarHistorico(Integer id) {
        historico.removeLastOccurrence(id);
        historico.addLast(id);
    }

    public Integer voltar() {
        if (!historico.isEmpty()) {
            historico.removeLast();
        }
        return historico.isEmpty() ? null : historico.peekLast();
    }
}
