package es.jaime.gateway.authentication._shared.infrastructure;

import es.jaime.gateway.authentication._shared.domain.User;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.authentication._shared.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(UserName.of(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername().value(),
                user.getPassword().value(),
                roles
        );
    }
}
