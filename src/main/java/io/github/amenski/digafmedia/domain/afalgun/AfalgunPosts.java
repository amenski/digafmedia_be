package io.github.amenski.digafmedia.domain.afalgun;

import java.util.List;

public record AfalgunPosts(List<AfalgunPost> posts) {
    
    public static AfalgunPosts of(List<AfalgunPost> posts) {
        return new AfalgunPosts(posts);
    }
}