package io.github.amenski.digafmedia.infrastructure.web.security;

import io.github.amenski.digafmedia.domain.CurrentUser;
import org.springframework.security.core.GrantedAuthority;

public class CurrentUserAdapter implements CurrentUser {
  private final UserPrincipal userPrincipal;

  public CurrentUserAdapter(UserPrincipal userPrincipal) {
    this.userPrincipal = userPrincipal;
  }

  @Override
  public String id() {
    return userPrincipal.getUsername();
  }

  @Override
  public String username() {
    return userPrincipal.getUsername();
  }

  @Override
  public boolean hasRole(String role) {
    String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
    return userPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(authority -> authority.equals(roleWithPrefix));
  }
}
