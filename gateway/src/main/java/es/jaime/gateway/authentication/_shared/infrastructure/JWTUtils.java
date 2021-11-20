package es.jaime.gateway.authentication._shared.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Try;

import java.time.ZonedDateTime;
import java.util.Date;

public final class JWTUtils {
    private JWTUtils() {}

    private final static String SECRET_KEY = "86n87ywisadf1ueyfnuistfyt";

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
                .compact();
    }

    public static boolean isValid (String token, String username) {
        Try<Boolean> validTry = Try.of(() ->  username.equalsIgnoreCase(getUsername(token)));

        return validTry.isSuccess() && validTry.get();
    }

    public static String getUsername (String token) {
        return getClaims(token).getSubject();
    }

    public static boolean isExpired (String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private static Claims getClaims (String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
