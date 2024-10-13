package io.github.amenski.digafmedia.domain;

import java.time.LocalDate;
import java.util.List;

public record Item(String title,
                   String contact,
                   String description,
                   List<String> images,
                   LocalDate publishedOn) {
}
