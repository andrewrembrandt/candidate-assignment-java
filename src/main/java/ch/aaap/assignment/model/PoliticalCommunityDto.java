package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.HashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class PoliticalCommunityDto implements PoliticalCommunity {
  private final String number;
  private final String name;
  private final String shortName;
  private final LocalDate lastUpdate;

  @ToString.Exclude @EqualsAndHashCode.Exclude private final District district;

  @ToString.Exclude @EqualsAndHashCode.Exclude
  private HashSet<PostalCommunity> postalCommunities = new HashSet<>();
}
