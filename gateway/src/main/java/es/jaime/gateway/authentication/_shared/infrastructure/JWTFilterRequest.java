package es.jaime.gateway.authentication._shared.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilterRequest extends OncePerRequestFilter {
    private final UserDetailsImpl userDetailsImpl;
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (isJWT(authorizationHeader) && isNotEmpty(authorizationHeader)) {

            String jwt = authorizationHeader.substring(7);
            String username = jwtUtils.getUsername(jwt);

            if (isNotAlreadyLogged()) {
                UserDetails userDetails = userDetailsImpl.loadUserByUsername(username);

                if (jwtUtils.isValid(jwt, username) && !jwtUtils.isExpired(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isJWT(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer");
    }

    private boolean isNotEmpty (String authorizationHeader) {
        return jwtUtils.getUsername(authorizationHeader.substring(7)) != null;
    }

    private boolean isNotAlreadyLogged () {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
