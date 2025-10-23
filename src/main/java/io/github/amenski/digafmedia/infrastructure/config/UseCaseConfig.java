package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.repository.*;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.*;
import io.github.amenski.digafmedia.usecase.freeservice.*;
import io.github.amenski.digafmedia.usecase.irdata.*;
import io.github.amenski.digafmedia.usecase.tikoma.*;
import io.github.amenski.digafmedia.usecase.withyou.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
  public CreateCommentUseCase createCommentUseCase(CommentRepository commentRepository, Validator<Comment> commentValidator) {
    return new CreateCommentUseCase(commentRepository, commentValidator);
  }

  @Bean
  public DeleteCommentUseCase deleteCommentUseCase(CommentRepository commentRepository) {
    return new DeleteCommentUseCase(commentRepository);
  }

  // Afalgun Use Cases
  @Bean
  public CreateAfalgunPostUseCase createAfalgunPostUseCase(AfalgunRepository afalgunRepository, Validator<AfalgunPost> afalgunPostValidator) {
    return new CreateAfalgunPostUseCase(afalgunRepository, afalgunPostValidator);
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
  public CreateIrdataPostUseCase createIrdataPostUseCase(IrdataRepository irdataRepository, Validator<IrdataPost> irdataPostValidator) {
    return new CreateIrdataPostUseCase(irdataRepository, irdataPostValidator);
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
  public CreateTikomaAlertUseCase createTikomaAlertUseCase(TikomaRepository tikomaRepository, Validator<TikomaAlert> tikomaAlertValidator) {
    return new CreateTikomaAlertUseCase(tikomaRepository, tikomaAlertValidator);
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
  public CreateFreeServiceUseCase createFreeServiceUseCase(FreeServiceRepository freeServiceRepository, Validator<FreeService> freeServiceValidator) {
    return new CreateFreeServiceUseCase(freeServiceRepository, freeServiceValidator);
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
  public CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase(WithYouRepository withYouRepository, Validator<WithYouTestimonial> withYouTestimonialValidator) {
    return new CreateWithYouTestimonialUseCase(withYouRepository, withYouTestimonialValidator);
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
}
