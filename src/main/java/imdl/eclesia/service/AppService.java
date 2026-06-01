package imdl.eclesia.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class AppService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public boolean hasRole(String roleName) {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + roleName) ||
                                                  grantedAuthority.getAuthority().equals(roleName));
        }
        return false;
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isLider() {
        return hasRole("Líder");
    }

    public boolean isAdminOrLider() {
        return isAdmin() || isLider();
    }
}
