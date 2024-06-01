package com.rakesh.authmodule.repository;

import com.rakesh.authmodule.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndIsDeleted(String value, boolean isDeleted);
}
