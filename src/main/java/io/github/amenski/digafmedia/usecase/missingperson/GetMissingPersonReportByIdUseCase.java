package io.github.amenski.digafmedia.usecase.missingperson;

import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;

import java.util.Optional;

public class GetMissingPersonReportByIdUseCase {

    private final MissingPersonReportRepository missingPersonReportRepository;

    public GetMissingPersonReportByIdUseCase(MissingPersonReportRepository missingPersonReportRepository) {
        this.missingPersonReportRepository = missingPersonReportRepository;
    }

    public Optional<MissingPersonReport> invoke(Long id) {
        return missingPersonReportRepository.findById(id);
    }
}