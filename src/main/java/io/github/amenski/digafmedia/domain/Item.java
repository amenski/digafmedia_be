package io.github.amenski.digafmedia.domain;

import java.util.List;

public record Item(String title,
                   String contact,
                   String description,
                   List<String> images,
                   String publishedOn) {
}
