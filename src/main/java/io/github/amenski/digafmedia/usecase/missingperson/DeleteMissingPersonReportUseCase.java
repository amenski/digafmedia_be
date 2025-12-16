package io.github.amenski.digafmedia.usecase.missingperson;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;

public class DeleteMissingPersonReportUseCase {

    private final MissingPersonReportRepository missingPersonReportRepository;

    public DeleteMissingPersonReportUseCase(MissingPersonReportRepository missingPersonReportRepository) {
        this.missingPersonReportRepository = missingPersonReportRepository;
    }

    public void invoke(Long id, CurrentUser currentUser) {
        // Only admins can delete reports
        if (!currentUser.hasRole("ADMIN")) {
            throw AuthorizationException.forOperation("delete missing person report");
        }

        // Check if entity exists before deletion
        if (!missingPersonReportRepository.existsById(id)) {
            throw EntityNotFoundException.forEntity("MissingPersonReport", id);
        }
        missingPersonReportRepository.deleteById(id);
    }
}