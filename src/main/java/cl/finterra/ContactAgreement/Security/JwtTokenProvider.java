package cl.finterra.ContactAgreement.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import cl.finterra.ContactAgreement.Security.TokenRefreshException;
import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

    //token de acceso secret key y access-refresh token
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long ACCESS_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000; // 24 horas

    public String generateAccessToken(String usuario) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(usuario)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }



    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public String renewAccessToken(String refreshToken) throws TokenRefreshException {
        try {
            Jws<Claims> refreshClaims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(refreshToken);

            // Verificar valides del token
            if (refreshClaims.getBody().getExpiration().before(new Date())) {
                throw new TokenRefreshException("Refresh token has expired");
            }

            // generar nuevo token acceso
            String username = refreshClaims.getBody().getSubject();
            return generateAccessToken(username);
        } catch (Exception ex) {
            throw new TokenRefreshException("Invalid refresh token", ex);
        }
    }
}