package io.github.amenski.digafmedia.domain;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public static EntityNotFoundException forEntity(String entityName, Long id) {
        return new EntityNotFoundException(entityName + " not found with id: " + id);
    }
}