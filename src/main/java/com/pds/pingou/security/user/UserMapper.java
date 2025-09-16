package com.pds.pingou.security.user;

import com.pds.pingou.security.auth.dto.RegisterRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterRequestDTO dto) {
        return new User(
                dto.email(),
                dto.nome(),
                dto.sobrenome(),
                passwordEncoder.encode(dto.password()),
                UserRole.USER
        );
    }
}