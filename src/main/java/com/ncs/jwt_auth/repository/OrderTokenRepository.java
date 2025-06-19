package com.ncs.jwt_auth.repository;

import com.ncs.jwt_auth.model.OrderToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTokenRepository extends JpaRepository<OrderToken, Long> {
    Optional<OrderToken> findByOrderId(String orderId);
}