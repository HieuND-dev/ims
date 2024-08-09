package be_im_interview_management.jwt;


import be_im_interview_management.exception.TokenErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

/**
 * Created by: HieuND64
 * Date Time: 7/25/2024 10:23 AM
 */
@Component
public class JwtProvider {

    @Value("${jwt.expiration:7d}")
    private Duration expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateAccessToken(String account) {

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiration.toMillis());

        return Jwts.builder().subject(account)
                .expiration(expireDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String verifyAndExtractUser(String token) {

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build().parseSignedClaims(token).getPayload();

            return claims.getSubject();

        } catch (JwtException e) {
            throw new TokenErrorException(e);
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
