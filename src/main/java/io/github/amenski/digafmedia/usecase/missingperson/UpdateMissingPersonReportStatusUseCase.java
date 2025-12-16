package io.github.amenski.digafmedia.usecase.missingperson;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;
import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;

public class UpdateMissingPersonReportStatusUseCase {

    private final MissingPersonReportRepository missingPersonReportRepository;

    public UpdateMissingPersonReportStatusUseCase(MissingPersonReportRepository missingPersonReportRepository) {
        this.missingPersonReportRepository = missingPersonReportRepository;
    }

    public MissingPersonReport invoke(Long id, ReportStatus status, CurrentUser currentUser) {
        // Only admins can update report status
        if (!currentUser.hasRole("ADMIN")) {
            throw AuthorizationException.forOperation("update missing person report status");
        }

        MissingPersonReport existing = missingPersonReportRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.forEntity("MissingPersonReport", id));

        MissingPersonReport updated = existing.updateStatus(status);
        return missingPersonReportRepository.save(updated);
    }
}