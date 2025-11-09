package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.freeservice.FreeServices;
import io.github.amenski.digafmedia.usecase.freeservice.CreateFreeServiceUseCase;
import io.github.amenski.digafmedia.usecase.freeservice.DeleteFreeServiceUseCase;
import io.github.amenski.digafmedia.usecase.freeservice.GetAllFreeServicesUseCase;
import io.github.amenski.digafmedia.usecase.freeservice.SearchFreeServicesUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/free-services")
public class FreeServiceController {

    private static final Logger log = LoggerFactory.getLogger(FreeServiceController.class);

    private final GetAllFreeServicesUseCase getAllFreeServicesUseCase;
    private final SearchFreeServicesUseCase searchFreeServicesUseCase;
    private final CreateFreeServiceUseCase createFreeServiceUseCase;
    private final DeleteFreeServiceUseCase deleteFreeServiceUseCase;

    public FreeServiceController(
            GetAllFreeServicesUseCase getAllFreeServicesUseCase,
            SearchFreeServicesUseCase searchFreeServicesUseCase,
            CreateFreeServiceUseCase createFreeServiceUseCase,
            DeleteFreeServiceUseCase deleteFreeServiceUseCase) {
        this.getAllFreeServicesUseCase = getAllFreeServicesUseCase;
        this.searchFreeServicesUseCase = searchFreeServicesUseCase;
        this.createFreeServiceUseCase = createFreeServiceUseCase;
        this.deleteFreeServiceUseCase = deleteFreeServiceUseCase;
    }

    @GetMapping
    public ResponseEntity<FreeServices> getAllServices(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters
            if (page < 0) {
                return ResponseEntity.badRequest().body(null);
            }
            if (size <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            FreeServices services = getAllFreeServicesUseCase.invoke(isActive, page, size);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            log.error("Error getting all free services", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<FreeServices> searchServices(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        try {
            FreeServices services = searchFreeServicesUseCase.invoke(category, location);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            log.error("Error searching free services", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<FreeService> createService(@RequestBody FreeServiceRequest request) {
        try {
            FreeService service = new FreeService(
                    null,
                    request.serviceName(),
                    request.providerName(),
                    request.description(),
                    request.location(),
                    request.contactPhone(),
                    request.contactEmail(),
                    request.category(),
                    request.hoursOfOperation(),
                    request.isActive(),
                    null,
                    null
            );
            FreeService createdService = createFreeServiceUseCase.invoke(service);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating free service: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating free service", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            deleteFreeServiceUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting free service with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record FreeServiceRequest(
            String serviceName,
            String providerName,
            String description,
            String location,
            String contactPhone,
            String contactEmail,
            String category,
            String hoursOfOperation,
            Boolean isActive
    ) {}
}
