package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.repository.*;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.*;
import io.github.amenski.digafmedia.usecase.auth.LoginUseCase;
import io.github.amenski.digafmedia.usecase.auth.RegisterUserUseCase;
import io.github.amenski.digafmedia.usecase.freeservice.*;
import io.github.amenski.digafmedia.usecase.irdata.*;
import io.github.amenski.digafmedia.usecase.tikoma.*;
import io.github.amenski.digafmedia.usecase.withyou.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UseCaseConfig {

  // Comment Use Cases
  @Bean
  public GetAllCommentsUseCase getAllCommentsUseCase(CommentRepository commentRepository) {
    return new GetAllCommentsUseCase(commentRepository);
  }

  @Bean
  public GetCommentByIdUseCase getCommentByIdUseCase(CommentRepository commentRepository) {
    return new GetCommentByIdUseCase(commentRepository);
  }

  @Bean
  public CreateCommentUseCase createCommentUseCase(CommentRepository commentRepository) {
    return new CreateCommentUseCase(commentRepository);
  }

  @Bean
  public DeleteCommentUseCase deleteCommentUseCase(CommentRepository commentRepository) {
    return new DeleteCommentUseCase(commentRepository);
  }

  // Afalgun Use Cases
  @Bean
  public CreateAfalgunPostUseCase createAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
    return new CreateAfalgunPostUseCase(afalgunRepository);
  }

  @Bean
  public GetAllAfalgunPostsUseCase getAllAfalgunPostsUseCase(AfalgunRepository afalgunRepository) {
    return new GetAllAfalgunPostsUseCase(afalgunRepository);
  }

  @Bean
  public GetAfalgunPostByIdUseCase getAfalgunPostByIdUseCase(AfalgunRepository afalgunRepository) {
    return new GetAfalgunPostByIdUseCase(afalgunRepository);
  }

  @Bean
  public UpdateAfalgunPostStatusUseCase updateAfalgunPostStatusUseCase(AfalgunRepository afalgunRepository) {
    return new UpdateAfalgunPostStatusUseCase(afalgunRepository);
  }

  @Bean
  public DeleteAfalgunPostUseCase deleteAfalgunPostUseCase(AfalgunRepository afalgunRepository) {
    return new DeleteAfalgunPostUseCase(afalgunRepository);
  }

  // Irdata Use Cases
  @Bean
  public CreateIrdataPostUseCase createIrdataPostUseCase(IrdataRepository irdataRepository) {
    return new CreateIrdataPostUseCase(irdataRepository);
  }

  @Bean
  public GetAllIrdataPostsUseCase getAllIrdataPostsUseCase(IrdataRepository irdataRepository) {
    return new GetAllIrdataPostsUseCase(irdataRepository);
  }

  @Bean
  public GetIrdataPostByIdUseCase getIrdataPostByIdUseCase(IrdataRepository irdataRepository) {
    return new GetIrdataPostByIdUseCase(irdataRepository);
  }

  @Bean
  public UpdateIrdataPostUseCase updateIrdataPostUseCase(IrdataRepository irdataRepository) {
    return new UpdateIrdataPostUseCase(irdataRepository);
  }

  @Bean
  public DeleteIrdataPostUseCase deleteIrdataPostUseCase(IrdataRepository irdataRepository) {
    return new DeleteIrdataPostUseCase(irdataRepository);
  }

  // Tikoma Use Cases
  @Bean
  public CreateTikomaAlertUseCase createTikomaAlertUseCase(TikomaRepository tikomaRepository) {
    return new CreateTikomaAlertUseCase(tikomaRepository);
  }

  @Bean
  public GetAllTikomaAlertsUseCase getAllTikomaAlertsUseCase(TikomaRepository tikomaRepository) {
    return new GetAllTikomaAlertsUseCase(tikomaRepository);
  }

  @Bean
  public DeleteTikomaAlertUseCase deleteTikomaAlertUseCase(TikomaRepository tikomaRepository) {
    return new DeleteTikomaAlertUseCase(tikomaRepository);
  }

  // Free Service Use Cases
  @Bean
  public CreateFreeServiceUseCase createFreeServiceUseCase(FreeServiceRepository freeServiceRepository) {
    return new CreateFreeServiceUseCase(freeServiceRepository);
  }

  @Bean
  public GetAllFreeServicesUseCase getAllFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
    return new GetAllFreeServicesUseCase(freeServiceRepository);
  }

  @Bean
  public SearchFreeServicesUseCase searchFreeServicesUseCase(FreeServiceRepository freeServiceRepository) {
    return new SearchFreeServicesUseCase(freeServiceRepository);
  }

  @Bean
  public DeleteFreeServiceUseCase deleteFreeServiceUseCase(FreeServiceRepository freeServiceRepository) {
    return new DeleteFreeServiceUseCase(freeServiceRepository);
  }

  // With You Use Cases
  @Bean
  public CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
    return new CreateWithYouTestimonialUseCase(withYouRepository);
  }

  @Bean
  public GetAllWithYouTestimonialsUseCase getAllWithYouTestimonialsUseCase(WithYouRepository withYouRepository) {
    return new GetAllWithYouTestimonialsUseCase(withYouRepository);
  }

  @Bean
  public ApproveWithYouTestimonialUseCase approveWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
    return new ApproveWithYouTestimonialUseCase(withYouRepository);
  }

  @Bean
  public DeleteWithYouTestimonialUseCase deleteWithYouTestimonialUseCase(WithYouRepository withYouRepository) {
    return new DeleteWithYouTestimonialUseCase(withYouRepository);
  }

  // Authentication Use Cases
  @Bean
  public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new RegisterUserUseCase(userRepository, passwordEncoder);
  }

  @Bean
  public LoginUseCase loginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new LoginUseCase(userRepository, passwordEncoder);
  }
}
