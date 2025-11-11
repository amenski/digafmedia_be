package io.github.amenski.digafmedia.domain;

import java.util.List;

public record Comments(List<Comment> result) {
    
    public static Comments of(List<Comment> result) {
        return new Comments(result);
    }
}