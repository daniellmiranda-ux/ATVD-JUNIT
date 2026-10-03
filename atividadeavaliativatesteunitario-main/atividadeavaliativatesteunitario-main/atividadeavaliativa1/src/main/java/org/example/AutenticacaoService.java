package org.example;

public class AutenticacaoService {

    private final String usuarioCadastrado = "usuarioValido";
    private final String senhaCadastrada = "Senha@12345";
    private boolean bloqueado = false;
    private int tentativasInvalidas = 0;

    private final ValidarSenha validator = new ValidarSenha();

    public boolean autenticar(String usuario, String senha) {
        // AUT12 e AUT08: Se o usuário já estiver bloqueado, rejeita imediatamente
        if (this.bloqueado) {
            return false;
        }

        // AUT04, AUT05, AUT06 e AUT07: Validação de nulos e vazios
        if (usuario == null || usuario.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return false;
        }

        // AUT02: Validação de existência do usuário
        if (!usuario.equals(usuarioCadastrado)) {
            return false;
        }

        // AUT03: Validação da senha (composição + igualdade)
        if (!validator.validarSenha(senha) || !senha.equals(senhaCadastrada)) {
            tentativasInvalidas++;

            // AUT11: Bloqueia na 3ª tentativa
            if (tentativasInvalidas >= 3) {
                this.bloqueado = true;
            }
            return false;
        }

        // AUT01: Autenticação com sucesso zera a contagem de tentativas
        tentativasInvalidas = 0;
        return true;
    }

    public boolean isBloqueado() {
        return this.bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
