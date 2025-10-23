package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonials;
import io.github.amenski.digafmedia.usecase.withyou.ApproveWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.CreateWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.DeleteWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.GetAllWithYouTestimonialsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/with-you")
public class WithYouController {

    private static final Logger log = LoggerFactory.getLogger(WithYouController.class);

    private final GetAllWithYouTestimonialsUseCase getAllWithYouTestimonialsUseCase;
    private final CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase;
    private final ApproveWithYouTestimonialUseCase approveWithYouTestimonialUseCase;
    private final DeleteWithYouTestimonialUseCase deleteWithYouTestimonialUseCase;

    public WithYouController(
            GetAllWithYouTestimonialsUseCase getAllWithYouTestimonialsUseCase,
            CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase,
            ApproveWithYouTestimonialUseCase approveWithYouTestimonialUseCase,
            DeleteWithYouTestimonialUseCase deleteWithYouTestimonialUseCase) {
        this.getAllWithYouTestimonialsUseCase = getAllWithYouTestimonialsUseCase;
        this.createWithYouTestimonialUseCase = createWithYouTestimonialUseCase;
        this.approveWithYouTestimonialUseCase = approveWithYouTestimonialUseCase;
        this.deleteWithYouTestimonialUseCase = deleteWithYouTestimonialUseCase;
    }

    @GetMapping
    public ResponseEntity<WithYouTestimonials> getAllTestimonials(@RequestParam(required = false) Boolean isApproved) {
        try {
            WithYouTestimonials testimonials = getAllWithYouTestimonialsUseCase.invoke(isApproved);
            return ResponseEntity.ok(testimonials);
        } catch (Exception e) {
            log.error("Error getting all with-you testimonials", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<WithYouTestimonial> createTestimonial(@RequestBody WithYouTestimonialRequest request) {
        try {
            WithYouTestimonial testimonial = new WithYouTestimonial(
                    null,
                    request.title(),
                    request.story(),
                    request.authorName(),
                    request.authorLocation(),
                    request.isApproved(),
                    null,
                    null
            );
            WithYouTestimonial createdTestimonial = createWithYouTestimonialUseCase.invoke(testimonial);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating with-you testimonial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating with-you testimonial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<WithYouTestimonial> approveTestimonial(@PathVariable Long id) {
        try {
            WithYouTestimonial approved = approveWithYouTestimonialUseCase.invoke(id);
            return ResponseEntity.ok(approved);
        } catch (IllegalArgumentException e) {
            log.warn("Testimonial not found with id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error approving with-you testimonial with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        try {
            deleteWithYouTestimonialUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting with-you testimonial with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record WithYouTestimonialRequest(
            String title,
            String story,
            String authorName,
            String authorLocation,
            Boolean isApproved
    ) {}
}
