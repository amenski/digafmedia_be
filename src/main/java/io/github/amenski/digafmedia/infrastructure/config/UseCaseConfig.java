package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.repository.*;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;
import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;
import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.CreateFundraisingCampaignUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.DeleteFundraisingCampaignUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.GetAllFundraisingCampaignsUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.GetFundraisingCampaignByIdUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.UpdateFundraisingCampaignAmountUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.UpdateFundraisingCampaignStatusUseCase;
import io.github.amenski.digafmedia.usecase.missingperson.*;
import io.github.amenski.digafmedia.usecase.auth.LoginUseCase;
import io.github.amenski.digafmedia.usecase.auth.RegisterUserUseCase;
import io.github.amenski.digafmedia.usecase.freeservice.*;
import io.github.amenski.digafmedia.usecase.communityalert.*;
import io.github.amenski.digafmedia.usecase.impactstory.*;
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

  // Missing Person Reports Use Cases
  @Bean
  public CreateMissingPersonReportUseCase createMissingPersonReportUseCase(MissingPersonReportRepository missingPersonReportRepository) {
    return new CreateMissingPersonReportUseCase(missingPersonReportRepository);
  }

  @Bean
  public GetAllMissingPersonReportsUseCase getAllMissingPersonReportsUseCase(MissingPersonReportRepository missingPersonReportRepository) {
    return new GetAllMissingPersonReportsUseCase(missingPersonReportRepository);
  }

  @Bean
  public GetMissingPersonReportByIdUseCase getMissingPersonReportByIdUseCase(MissingPersonReportRepository missingPersonReportRepository) {
    return new GetMissingPersonReportByIdUseCase(missingPersonReportRepository);
  }

  @Bean
  public UpdateMissingPersonReportStatusUseCase updateMissingPersonReportStatusUseCase(MissingPersonReportRepository missingPersonReportRepository) {
    return new UpdateMissingPersonReportStatusUseCase(missingPersonReportRepository);
  }

  @Bean
  public DeleteMissingPersonReportUseCase deleteMissingPersonReportUseCase(MissingPersonReportRepository missingPersonReportRepository) {
    return new DeleteMissingPersonReportUseCase(missingPersonReportRepository);
  }

  // Fundraising Campaign Use Cases
  @Bean
  public CreateFundraisingCampaignUseCase createFundraisingCampaignUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new CreateFundraisingCampaignUseCase(fundraisingCampaignRepository);
  }

  @Bean
  public GetAllFundraisingCampaignsUseCase getAllFundraisingCampaignsUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new GetAllFundraisingCampaignsUseCase(fundraisingCampaignRepository);
  }

  @Bean
  public GetFundraisingCampaignByIdUseCase getFundraisingCampaignByIdUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new GetFundraisingCampaignByIdUseCase(fundraisingCampaignRepository);
  }

  @Bean
  public UpdateFundraisingCampaignAmountUseCase updateFundraisingCampaignAmountUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new UpdateFundraisingCampaignAmountUseCase(fundraisingCampaignRepository);
  }

  @Bean
  public UpdateFundraisingCampaignStatusUseCase updateFundraisingCampaignStatusUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new UpdateFundraisingCampaignStatusUseCase(fundraisingCampaignRepository);
  }

  @Bean
  public DeleteFundraisingCampaignUseCase deleteFundraisingCampaignUseCase(FundraisingCampaignRepository fundraisingCampaignRepository) {
    return new DeleteFundraisingCampaignUseCase(fundraisingCampaignRepository);
  }

  // Community Alert Use Cases
  @Bean
  public CreateCommunityAlertUseCase createCommunityAlertUseCase(CommunityAlertRepository communityAlertRepository) {
    return new CreateCommunityAlertUseCase(communityAlertRepository);
  }

  @Bean
  public GetAllCommunityAlertsUseCase getAllCommunityAlertsUseCase(CommunityAlertRepository communityAlertRepository) {
    return new GetAllCommunityAlertsUseCase(communityAlertRepository);
  }

  @Bean
  public GetCommunityAlertByIdUseCase getCommunityAlertByIdUseCase(CommunityAlertRepository communityAlertRepository) {
    return new GetCommunityAlertByIdUseCase(communityAlertRepository);
  }

  @Bean
  public UpdateCommunityAlertUrgencyUseCase updateCommunityAlertUrgencyUseCase(CommunityAlertRepository communityAlertRepository) {
    return new UpdateCommunityAlertUrgencyUseCase(communityAlertRepository);
  }

  @Bean
  public DeleteCommunityAlertUseCase deleteCommunityAlertUseCase(CommunityAlertRepository communityAlertRepository) {
    return new DeleteCommunityAlertUseCase(communityAlertRepository);
  }

  // Community Resource Use Cases
  @Bean
  public CreateCommunityResourceUseCase createCommunityResourceUseCase(CommunityResourceRepository communityResourceRepository) {
    return new CreateCommunityResourceUseCase(communityResourceRepository);
  }

  @Bean
  public GetAllCommunityResourcesUseCase getAllCommunityResourcesUseCase(CommunityResourceRepository communityResourceRepository) {
    return new GetAllCommunityResourcesUseCase(communityResourceRepository);
  }

  @Bean
  public SearchCommunityResourcesUseCase searchCommunityResourcesUseCase(CommunityResourceRepository communityResourceRepository) {
    return new SearchCommunityResourcesUseCase(communityResourceRepository);
  }

  @Bean
  public DeleteCommunityResourceUseCase deleteCommunityResourceUseCase(CommunityResourceRepository communityResourceRepository) {
    return new DeleteCommunityResourceUseCase(communityResourceRepository);
  }

  // Impact Story Use Cases
  @Bean
  public CreateImpactStoryUseCase createImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
    return new CreateImpactStoryUseCase(impactStoryRepository);
  }

  @Bean
  public GetAllImpactStoriesUseCase getAllImpactStoriesUseCase(ImpactStoryRepository impactStoryRepository) {
    return new GetAllImpactStoriesUseCase(impactStoryRepository);
  }

  @Bean
  public GetImpactStoryByIdUseCase getImpactStoryByIdUseCase(ImpactStoryRepository impactStoryRepository) {
    return new GetImpactStoryByIdUseCase(impactStoryRepository);
  }

  @Bean
  public ApproveImpactStoryUseCase approveImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
    return new ApproveImpactStoryUseCase(impactStoryRepository);
  }

  @Bean
  public DeleteImpactStoryUseCase deleteImpactStoryUseCase(ImpactStoryRepository impactStoryRepository) {
    return new DeleteImpactStoryUseCase(impactStoryRepository);
  }

  // Authentication Use Cases
  @Bean
  public RegisterUserUseCase registerUserUseCase(AccountRepository accountRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
    return new RegisterUserUseCase(accountRepository, userProfileRepository, passwordEncoder);
  }

  @Bean
  public LoginUseCase loginUseCase(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    return new LoginUseCase(accountRepository, passwordEncoder);
  }
}
