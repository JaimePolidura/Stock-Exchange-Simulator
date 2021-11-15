package es.jaime.gateway.authentication._shared.infrastrocture;

import es.jaime.gateway.authentication._shared.domain.User;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.authentication._shared.domain.UserPassword;
import es.jaime.gateway.authentication._shared.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Repository
public class UserRepositoryInMemory implements UserRepository {
    private final Set<User> users = new HashSet<>() {{
        add(new User(UserName.of("jaime"), UserPassword.of("kjahskjlahkwjh1k2jh")));
        add(new User(UserName.of("alejandro"), UserPassword.of("lsiaydgfbkuasjhf")));
    }};

    @Override
    public Optional<User> findByUsername(UserName username) {
        return users.stream()
                .filter(user -> user.getUsername().value().equalsIgnoreCase(username.value()))
                .findFirst();
    }

    @Override
    public Boolean existsByUsername(UserName username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().value().equalsIgnoreCase(username.value()));
    }
}
