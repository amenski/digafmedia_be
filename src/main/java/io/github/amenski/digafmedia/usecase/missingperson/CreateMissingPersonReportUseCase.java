package io.github.amenski.digafmedia.usecase.missingperson;

import io.github.amenski.digafmedia.domain.CurrentUser;
import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.CreateMissingPersonReportCommand;
import io.github.amenski.digafmedia.domain.exception.ValidationException;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;
import io.github.amenski.digafmedia.domain.rules.MissingPersonReportValidator;

public class CreateMissingPersonReportUseCase {
    
    private final MissingPersonReportRepository missingPersonReportRepository;

    public CreateMissingPersonReportUseCase(MissingPersonReportRepository missingPersonReportRepository) {
        this.missingPersonReportRepository = missingPersonReportRepository;
    }

    public MissingPersonReport execute(CreateMissingPersonReportCommand command, CurrentUser currentUser) {
        var validationResult = MissingPersonReportValidator.validateCreateCommand(command);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult);
        }
        
        MissingPersonReport report = MissingPersonReport.fromCommand(command, currentUser.id());
        return missingPersonReportRepository.save(report);
    }
}