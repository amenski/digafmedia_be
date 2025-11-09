package io.github.amenski.digafmedia.infrastructure.web.security;

public class CurrentUserAdapter {
    private final UserPrincipal userPrincipal;

    public CurrentUserAdapter(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public String getUsername() {
        return userPrincipal.getUsername();
    }

    // You can add more methods to get user details as needed
    // For example: getUserId, getAuthorities, etc.
}
