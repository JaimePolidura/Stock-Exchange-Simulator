package es.jaime.gateway.authentication._shared.domain;


import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(UserName username);

    Boolean existsByUsername(UserName username);
}
