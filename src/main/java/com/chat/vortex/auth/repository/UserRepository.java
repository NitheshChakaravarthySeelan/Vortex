package com.chat.vortex.auth.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.chat.vortex.auth.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
  Mono<User> findByUserName(String userName);

  Mono<Boolean> existsByEmail(String email);

}
