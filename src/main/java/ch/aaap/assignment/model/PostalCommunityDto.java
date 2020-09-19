package ch.aaap.assignment.model;

import java.util.HashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class PostalCommunityDto implements PostalCommunity {
  private final String zipCode;
  private final String zipCodeAddition;
  private final String name;

  @ToString.Exclude @EqualsAndHashCode.Exclude private final Canton canton;

  @ToString.Exclude @EqualsAndHashCode.Exclude
  private HashSet<PoliticalCommunity> politicalCommunities = new HashSet<>();
}
