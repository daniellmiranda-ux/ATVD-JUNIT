package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutenticacaoServiceTest {

    private AutenticacaoService service;

    @BeforeEach
    public void setUp() {
        service = new AutenticacaoService();
    }

    // AUT01 - Usuário válido + senha válida
    @Test
    public void aut01_deveAutenticarUsuarioESenhaValidos() {
        boolean resultado = service.autenticar("usuarioValido", "Senha@12345");
        assertTrue(resultado);
    }

    // AUT02 - Usuário inexistente
    @Test
    public void aut02_deveRejeitarUsuarioInexistente() {
        boolean resultado = service.autenticar("usuarioInexistente", "Senha@12345");
        assertFalse(resultado);
    }

    // AUT03 - Senha inválida
    @Test
    public void aut03_deveRejeitarSenhaInvalida() {
        boolean resultado = service.autenticar("usuarioValido", "SenhaErrada1");
        assertFalse(resultado);
    }

    // AUT04 - Usuário nulo
    @Test
    public void aut04_deveRejeitarUsuarioNulo() {
        boolean resultado = service.autenticar(null, "Senha@12345");
        assertFalse(resultado);
    }

    // AUT05 - Senha nula
    @Test
    public void aut05_deveRejeitarSenhaNula() {
        boolean resultado = service.autenticar("usuarioValido", null);
        assertFalse(resultado);
    }

    // AUT06 - Usuário vazio
    @Test
    public void aut06_deveRejeitarUsuarioVazio() {
        boolean resultado = service.autenticar("", "Senha@12345");
        assertFalse(resultado);
    }

    // AUT07 - Senha vazia
    @Test
    public void aut07_deveRejeitarSenhaVazia() {
        boolean resultado = service.autenticar("usuarioValido", "");
        assertFalse(resultado);
    }

    // AUT08 - Usuário bloqueado
    @Test
    public void aut08_deveRejeitarUsuarioBloqueado() {
        service.setBloqueado(true);
        boolean resultado = service.autenticar("usuarioValido", "Senha@12345");
        assertFalse(resultado);
    }

    // AUT09 - 1ª tentativa inválida (Permanecer desbloqueado)
    @Test
    public void aut09_primeiraTentativaInvalidaDeveManterDesbloqueado() {
        boolean resultado = service.autenticar("usuarioValido", "SenhaInvalida1");
        assertFalse(resultado);
        assertFalse(service.isBloqueado());
    }

    // AUT10 - 2ª tentativa inválida (Permanecer desbloqueado)
    @Test
    public void aut10_segundaTentativaInvalidaDeveManterDesbloqueado() {
        service.autenticar("usuarioValido", "SenhaInvalida1"); // 1ª tentativa
        boolean resultado = service.autenticar("usuarioValido", "SenhaInvalida2"); // 2ª tentativa

        assertFalse(resultado);
        assertFalse(service.isBloqueado());
    }

    // AUT11 - 3ª tentativa inválida (Bloquear)
    @Test
    public void aut11_terceiraTentativaInvalidaDeveBloquear() {
        service.autenticar("usuarioValido", "SenhaInvalida1"); // 1ª tentativa
        service.autenticar("usuarioValido", "SenhaInvalida2"); // 2ª tentativa
        boolean resultado = service.autenticar("usuarioValido", "SenhaInvalida3"); // 3ª tentativa

        assertFalse(resultado);
        assertTrue(service.isBloqueado());
    }

    // AUT12 - Tentativa após bloqueio
    @Test
    public void aut12_tentativaAposBloqueioDeveSerRejeitada() {
        // Força o bloqueio com 3 tentativas incorretas
        service.autenticar("usuarioValido", "SenhaInvalida1");
        service.autenticar("usuarioValido", "SenhaInvalida2");
        service.autenticar("usuarioValido", "SenhaInvalida3");

        // Tentativa de login com senha correta após o bloqueio
        boolean resultado = service.autenticar("usuarioValido", "Senha@12345");
        assertFalse(resultado);
    }
}
