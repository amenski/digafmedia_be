package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.repository.FundraisingCampaignRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.FundraisingCampaignEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class FundraisingCampaignDbRepository implements FundraisingCampaignRepository {

    private final FundraisingCampaignJpaRepository fundraisingCampaignJpaRepository;

    public FundraisingCampaignDbRepository(FundraisingCampaignJpaRepository fundraisingCampaignJpaRepository) {
        this.fundraisingCampaignJpaRepository = fundraisingCampaignJpaRepository;
    }

    @Override
    public List<FundraisingCampaign> findRecent(int limit) {
        return fundraisingCampaignJpaRepository.findRecent(limit).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<FundraisingCampaign> findRecentByStatus(CampaignStatus status, int limit) {
        return fundraisingCampaignJpaRepository.findRecentByStatus(status, limit).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<FundraisingCampaign> findById(Long id) {
        return fundraisingCampaignJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return fundraisingCampaignJpaRepository.existsById(id);
    }

    @Override
    public FundraisingCampaign save(FundraisingCampaign campaign) {
        FundraisingCampaignEntity entity = toEntity(campaign);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(fundraisingCampaignJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        fundraisingCampaignJpaRepository.deleteById(id);
    }

    @Override
    public List<FundraisingCampaign> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fundraisingCampaignJpaRepository.findAll(pageable).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<FundraisingCampaign> findByStatusPaginated(CampaignStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fundraisingCampaignJpaRepository.findByStatus(status, pageable).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return fundraisingCampaignJpaRepository.count();
    }

    @Override
    public long countByStatus(CampaignStatus status) {
        return fundraisingCampaignJpaRepository.countByStatus(status);
    }

    private FundraisingCampaign toDomain(FundraisingCampaignEntity entity) {
        return new FundraisingCampaign(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGoalAmount(),
                entity.getCurrentAmount(),
                entity.getBankName(),
                entity.getAccountNumber(),
                entity.getAccountHolder(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getCreatedBy()
        );
    }

    private FundraisingCampaignEntity toEntity(FundraisingCampaign campaign) {
        FundraisingCampaignEntity entity = new FundraisingCampaignEntity();
        entity.setId(campaign.id());
        entity.setTitle(campaign.title());
        entity.setDescription(campaign.description());
        entity.setGoalAmount(campaign.goalAmount());
        entity.setCurrentAmount(campaign.currentAmount());
        entity.setBankName(campaign.bankName());
        entity.setAccountNumber(campaign.accountNumber());
        entity.setAccountHolder(campaign.accountHolder());
        entity.setContactName(campaign.contactName());
        entity.setContactPhone(campaign.contactPhone());
        entity.setContactEmail(campaign.contactEmail());
        entity.setStatus(campaign.status());
        entity.setCreatedBy(campaign.createdBy());
        return entity;
    }
}
