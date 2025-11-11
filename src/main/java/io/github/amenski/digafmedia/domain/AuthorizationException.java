package io.github.amenski.digafmedia.domain;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
    
    public static AuthorizationException forOperation(String operation) {
        return new AuthorizationException("User not authorized to perform: " + operation);
    }
    
    public static AuthorizationException forEntity(String entityName, Long id) {
        return new AuthorizationException("User not authorized to access " + entityName + " with id: " + id);
    }
}