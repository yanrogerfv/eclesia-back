package imdl.eclesia.auth.filter;

import imdl.eclesia.auth.configuration.JwtUtil;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

        try {
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
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
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

                    // Refresh token with new expiration time
                    String refreshedToken = JwtUtil.refreshToken(username);
                    response.setHeader("X-Refreshed-Token", refreshedToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Acesso inválido ou expirado");
        } catch (EntityNotFoundException e) {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (UnauthorizedException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro durante a autenticação, favor contatar o suporte.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("{\"error\":[\"%s\"]}", message.replace("\"", "\\\""));
        response.getWriter().write(jsonResponse);
    }

}