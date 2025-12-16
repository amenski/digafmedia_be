package io.github.amenski.digafmedia.domain.missingperson;

import java.util.List;

public record MissingPersonReports(List<MissingPersonReport> reports) {
    
    public static MissingPersonReports of(List<MissingPersonReport> reports) {
        return new MissingPersonReports(reports);
    }
}