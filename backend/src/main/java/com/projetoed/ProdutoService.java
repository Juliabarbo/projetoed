package com.projetoed;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProduto(Integer id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> listarRelacionados(Integer id) {
        return produtoRepository.findAll().stream()
            .filter(produto -> !produto.getId().equals(id))
            .toList();
    }

    @Bean
    CommandLineRunner iniciarDados(UsuarioRepository usuarioRepository) {
        return args -> {
            var usuario = usuarioRepository.findByEmail("cliente@hotmail.com");
            if (usuario.isPresent()) {
                usuario.get().setSenha("123");
                usuarioRepository.save(usuario.get());
            } else {
                usuarioRepository.save(new Usuario("cliente@hotmail.com", "123"));
            }

            if (produtoRepository.count() == 0) {
                produtoRepository.saveAll(List.of(
                    new Produto(1, "Fone de Ouvido Bluetooth", 249.9, "https://picsum.photos/id/1/500/500", "Fone sem fio com cancelamento de ruído e bateria de até 30 horas."),
                    new Produto(2, "Smartwatch Fit", 399.9, "https://picsum.photos/id/20/500/500", "Relógio inteligente com monitor de batimentos, GPS e resistência à água."),
                    new Produto(3, "Mochila para Notebook", 159.9, "https://picsum.photos/id/26/500/500", "Mochila resistente à água com compartimento acolchoado para notebook."),
                    new Produto(4, "Teclado Mecânico", 329.9, "https://picsum.photos/id/60/500/500", "Teclado mecânico com switches azuis, iluminação RGB e estrutura em alumínio.")
                ));
            }
        };
    }
}
