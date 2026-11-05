package com.projetoed;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {
    private final LoginService loginService;
    private final ProdutoService produtoService;
    private final NavegacaoService navegacaoService;
    private final PedidoService pedidoService;

    public ApiController(LoginService loginService, ProdutoService produtoService,
                         NavegacaoService navegacaoService, PedidoService pedidoService) {
        this.loginService = loginService;
        this.produtoService = produtoService;
        this.navegacaoService = navegacaoService;
        this.pedidoService = pedidoService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dados) {
        if (!loginService.autenticar(dados)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciais inválidas"));
        }

        return ResponseEntity.ok(Map.of("message", "Login realizado"));
    }

    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        // Lista consumida pelos cards da página de opções de produtos.
        return produtoService.listarProdutos();
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<?> buscarProduto(@PathVariable int id) {
        return produtoService.buscarProduto(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/produtos/{id}/relacionados")
    public List<Produto> produtosRelacionados(@PathVariable int id) {
        // Exclui o produto atual da lista de produtos relacionados.
        return produtoService.listarRelacionados(id);
    }

    @PostMapping("/historico/{id}")
    public void adicionarHistorico(@PathVariable int id) {
        navegacaoService.adicionarHistorico(id);
    }

    @PostMapping("/historico/voltar")
    public Map<String, Object> voltarHistorico() {
        Integer anterior = navegacaoService.voltar();
        if (anterior == null) {
            return Map.of("anterior", null);
        }
        return Map.of("anterior", Map.of("id", anterior));
    }

    @PostMapping("/pedidos/{id}")
    public Map<String, Integer> adicionarPedido(@PathVariable int id) {
        return Map.of("posicao", pedidoService.adicionarPedido(id));
    }

}
