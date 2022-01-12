package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.authentication._shared.domain.UserPassword;
import lombok.Getter;

public final class AuthenticateCommand implements Command {
    @Getter private final UserName username;
    @Getter private final UserPassword password;

    public AuthenticateCommand(String username, String password) {
        this.username = UserName.of(username);
        this.password = UserPassword.of(password);
    }
}
