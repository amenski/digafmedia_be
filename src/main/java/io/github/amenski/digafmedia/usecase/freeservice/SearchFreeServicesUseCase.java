package io.github.amenski.digafmedia.usecase.freeservice;

import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;

public class SearchFreeServicesUseCase {

    private final FreeServiceRepository freeServiceRepository;

    public SearchFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
        this.freeServiceRepository = freeServiceRepository;
    }

    public PagedResult<FreeService> invoke(String category, String location, int page, int size) {
        // For now, we'll use the existing repository methods
        // In a real implementation, we would add proper search repository methods
        
        if (category != null && !category.trim().isEmpty()) {
            // Fallback to all active services when category is provided
            // This would need to be enhanced with proper category search with pagination
            var services = freeServiceRepository.findByActivePaginated(true, page, size);
            var totalElements = freeServiceRepository.countByActive(true);
            return new PagedResult<>(services, totalElements, page, size);
        }
        if (location != null && !location.trim().isEmpty()) {
            // Fallback to all active services when location is provided
            // This would need to be enhanced with proper location search with pagination
            var services = freeServiceRepository.findByActivePaginated(true, page, size);
            var totalElements = freeServiceRepository.countByActive(true);
            return new PagedResult<>(services, totalElements, page, size);
        }
        // Default to active services
        var services = freeServiceRepository.findByActivePaginated(true, page, size);
        var totalElements = freeServiceRepository.countByActive(true);
        return new PagedResult<>(services, totalElements, page, size);
    }
}
