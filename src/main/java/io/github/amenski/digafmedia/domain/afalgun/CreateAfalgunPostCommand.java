package io.github.amenski.digafmedia.domain.afalgun;


public record CreateAfalgunPostCommand(
    String missingPersonName,
    Integer age,
    String lastSeenLocation,
    String contactName,
    String contactPhone,
    String contactEmail,
    String description,
    AfalgunStatus status
) {
    public CreateAfalgunPostCommand {
        if (status == null) {
            status = AfalgunStatus.ACTIVE;
        }
    }
}
