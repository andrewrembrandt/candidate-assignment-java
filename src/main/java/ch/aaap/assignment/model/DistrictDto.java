package ch.aaap.assignment.model;

import java.util.HashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class DistrictDto implements District {
  private final String number;
  private final String name;

  @ToString.Exclude @EqualsAndHashCode.Exclude private final Canton canton;

  @ToString.Exclude private HashSet<PoliticalCommunity> politicalCommunities = new HashSet<>();

  @ToString.Exclude private HashSet<PostalCommunity> postalCommunities = new HashSet<>();
}
