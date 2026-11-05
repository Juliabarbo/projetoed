package com.projetoed;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean autenticar(LoginRequestDTO dados) {
        return usuarioRepository.findByEmail(dados.email() == null ? "" : dados.email())
            .map(usuario -> usuario.getSenha().equals(dados.senha() == null ? "" : dados.senha()))
            .orElse(false);
    }
}
