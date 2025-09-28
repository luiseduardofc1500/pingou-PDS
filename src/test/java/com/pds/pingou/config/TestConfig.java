package com.pds.pingou.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração para testes unitários.
 * 
 * Esta classe fornece beans mockados ou simplificados para uso nos testes,
 * evitando dependências externas e facilitando a execução dos testes.
 */
@TestConfiguration
public class TestConfig {

    /**
     * Bean do PasswordEncoder para usar nos testes.
     * Evita problemas de injeção de dependência nos testes de AuthenticationService.
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}