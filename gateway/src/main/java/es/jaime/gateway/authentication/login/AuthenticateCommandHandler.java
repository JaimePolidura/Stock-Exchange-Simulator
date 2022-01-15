package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.authentication._shared.domain.User;
import es.jaime.gateway.authentication._shared.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticateCommandHandler implements CommandHandler<AuthenticateCommand> {
    private final UserRepository userRepository;

    @Override
    public void handle(AuthenticateCommand command) {
        Optional<User> userOptional = this.userRepository.findByUsername(command.getUsername());

        if(userOptional.isEmpty()){
            throw new ResourceNotFound("User not found");
        }
        if(!userOptional.get().getUserPassword().equals(command.getPassword())){
            throw new ResourceNotFound("Password mismatch");
        }
    }
}
