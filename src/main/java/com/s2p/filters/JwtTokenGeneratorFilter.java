package com.s2p.filters;

import com.s2p.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    private static final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_THRESHOLD = 1000 * 60 * 2;    // 2 minutes before expiry

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String secret = ApplicationConstants.JWT_SECRET_DEFAULT_VALUE;
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            boolean needsRefresh = true; // default: generate new token
            String incomingToken = request.getHeader(ApplicationConstants.JWT_HEADER);

            if (incomingToken != null) {
                try {
                    Jws<Claims> claimsJws = Jwts.parser()
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(incomingToken);

                    Date expiry = claimsJws.getPayload().getExpiration();
                    long timeLeft = expiry.getTime() - System.currentTimeMillis();

                    // only refresh if token is near expiry
                    needsRefresh = timeLeft < REFRESH_THRESHOLD;

                } catch (JwtException e) {
                    // invalid/expired token → issue new one
                    needsRefresh = true;
                }
            }

            if (needsRefresh) {
                String jwtToken = Jwts.builder()
                        .subject(authentication.getName())
                        .issuer("S2P-Dev-Team")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                        .signWith(secretKey)
                        .compact();

                response.setHeader(ApplicationConstants.JWT_HEADER, jwtToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Run for all secured endpoints (not just register)
        return request.getServletPath().startsWith("/api/v1/public");
    }
}
