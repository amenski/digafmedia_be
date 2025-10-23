package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlerts;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.usecase.tikoma.CreateTikomaAlertUseCase;
import io.github.amenski.digafmedia.usecase.tikoma.DeleteTikomaAlertUseCase;
import io.github.amenski.digafmedia.usecase.tikoma.GetAllTikomaAlertsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tikoma")
public class TikomaController {

    private static final Logger log = LoggerFactory.getLogger(TikomaController.class);

    private final GetAllTikomaAlertsUseCase getAllTikomaAlertsUseCase;
    private final CreateTikomaAlertUseCase createTikomaAlertUseCase;
    private final DeleteTikomaAlertUseCase deleteTikomaAlertUseCase;

    public TikomaController(
            GetAllTikomaAlertsUseCase getAllTikomaAlertsUseCase,
            CreateTikomaAlertUseCase createTikomaAlertUseCase,
            DeleteTikomaAlertUseCase deleteTikomaAlertUseCase) {
        this.getAllTikomaAlertsUseCase = getAllTikomaAlertsUseCase;
        this.createTikomaAlertUseCase = createTikomaAlertUseCase;
        this.deleteTikomaAlertUseCase = deleteTikomaAlertUseCase;
    }

    @GetMapping
    public ResponseEntity<TikomaAlerts> getAllAlerts(@RequestParam(required = false, defaultValue = "10") Integer limit) {
        try {
            TikomaAlerts alerts = getAllTikomaAlertsUseCase.invoke(limit);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            log.error("Error getting all tikoma alerts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<TikomaAlert> createAlert(@RequestBody TikomaAlertRequest request) {
        try {
            TikomaAlert alert = new TikomaAlert(
                    null,
                    request.title(),
                    request.message(),
                    request.contactName(),
                    request.contactPhone(),
                    request.urgency(),
                    null,
                    null
            );
            TikomaAlert createdAlert = createTikomaAlertUseCase.invoke(alert);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating tikoma alert: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating tikoma alert", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        try {
            deleteTikomaAlertUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting tikoma alert with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record TikomaAlertRequest(
            String title,
            String message,
            TikomaUrgency urgency,
            String contactName,
            String contactPhone
    ) {}
}
