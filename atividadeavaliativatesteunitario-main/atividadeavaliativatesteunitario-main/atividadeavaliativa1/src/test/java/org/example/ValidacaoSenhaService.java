package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidacaoSenhaService {

    private ValidarSenha service = new ValidarSenha();

    // CT01: Senha válida (RF02/RF03)
    @Test
    public void ct01_deveAceitarSenhaValida() {
        String senha = "Java@123456";
        boolean resultado = service.validarSenha(senha);
        assertTrue(resultado);
    }

    // CT02: Menor que 10 caracteres (RF02)
    @Test
    public void ct02_deveRejeitarSenhaMenorQue10Caracteres() {
        String senha = "Java@1234";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT03: Maior que 12 caracteres (RF02)
    @Test
    public void ct03_deveRejeitarSenhaMaiorQue12Caracteres() {
        String senha = "Java@12345678";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT04: Sem número (RF03)
    @Test
    public void ct04_deveRejeitarSenhaSemNumero() {
        String senha = "Java@Testes";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT05: Sem letra (RF03)
    @Test
    public void ct05_deveRejeitarSenhaSemLetra() {
        String senha = "123456@7890";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT06: Sem caractere especial (RF03)
    @Test
    public void ct06_deveRejeitarSenhaSemEspecial() {
        String senha = "Java1234567";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT07: Senha nula (RF04)
    @Test
    public void ct07_deveRejeitarSenhaNula() {
        String senha = null;
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT08: Senha vazia (RF04)
    @Test
    public void ct08_deveRejeitarSenhaVazia() {
        String senha = "";
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // CT09: Exatamente 10 caracteres (RF02)
    @Test
    public void ct09_deveAceitarSenhaComExatamente10Caracteres() {
        String senha = "Java@12345";
        boolean resultado = service.validarSenha(senha);
        assertTrue(resultado);
    }

    // CT10: Exatamente 12 caracteres (RF02)
    @Test
    public void ct10_deveAceitarSenhaComExatamente12Caracteres() {
        String senha = "Java@1234567";
        boolean resultado = service.validarSenha(senha);
        assertTrue(resultado);
    }

    // Teste de Valor Limite Intermediário (11 caracteres - RF02)
    @Test
    public void ct17_deveAceitarSenhaComExatamente11Caracteres() {
        String senha = "Java@123456"; // 11 caracteres válidos
        boolean resultado = service.validarSenha(senha);
        assertTrue(resultado);
    }

    // Teste de Valor Limite - Exatamente 9 caracteres (Abaixo do mínimo - RF02)
    @Test
    public void ct18_deveRejeitarSenhaComExatamente9Caracteres() {
        String senha = "Java@1234"; // 9 caracteres válidos quanto à composição, mas inválida no tamanho
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }

    // Teste de Valor Limite - Exatamente 13 caracteres (Acima do máximo - RF02)
    @Test
    public void ct19_deveRejeitarSenhaComExatamente13Caracteres() {
        String senha = "Java@12345678"; // 13 caracteres válidos quanto à composição, mas inválida no tamanho
        boolean resultado = service.validarSenha(senha);
        assertFalse(resultado);
    }
}