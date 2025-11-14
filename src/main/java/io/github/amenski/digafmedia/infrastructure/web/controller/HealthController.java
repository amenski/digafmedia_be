package io.github.amenski.digafmedia.infrastructure.web.controller;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/status")
public class HealthController {

    private final ApplicationAvailability applicationAvailability;

    public HealthController(ApplicationAvailability applicationAvailability) {
        this.applicationAvailability = applicationAvailability;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        AvailabilityState livenessState = applicationAvailability.getLivenessState();
        AvailabilityState readinessState = applicationAvailability.getReadinessState();

        boolean isLive = livenessState == LivenessState.CORRECT;
        boolean isReady = readinessState == ReadinessState.ACCEPTING_TRAFFIC;

        Map<String, Object> healthStatus = Map.of(
            "status", isReady ? "UP" : "DOWN",
            "components", Map.of(
                "liveness", Map.of(
                    "status", isLive ? "UP" : "DOWN",
                    "details", Map.of("state", livenessState)
                ),
                "readiness", Map.of(
                    "status", isReady ? "UP" : "DOWN", 
                    "details", Map.of("state", readinessState)
                )
            )
        );

        return isReady ? 
            ResponseEntity.ok(healthStatus) : 
            ResponseEntity.status(503).body(healthStatus);
    }
}