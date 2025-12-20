package imdl.eclesia.auth.filter;

import imdl.eclesia.auth.configuration.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private final String[] PUBLIC_URLS = {
            "/auth/login",
            "/auth/register",
            "/auth/update",
            "/v3/api-docs/",
            "/swagger-ui",
            "/h2-console",
            "/error",
            "v1/escala/"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if the request URL is public
        for (String publicUrl : PUBLIC_URLS) {
            if (request.getRequestURI().contains("v1/escala/") && request.getMethod().equals("DELETE")) {
                break; // Skip allowing DELETE on /v1/escala/
            }
            if (request.getRequestURI().contains(publicUrl)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        final String authToken = request.getHeader("Authorization");

        if (authToken == null || authToken.contains("undefined")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        Claims claims = JwtUtil.validateToken(authToken);
        String username = claims.getSubject();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (JwtUtil.validateToken(authToken) != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}