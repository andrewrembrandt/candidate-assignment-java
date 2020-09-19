package ch.aaap.assignment.model;

import java.util.HashSet;
import lombok.Data;

@Data
public class ModelDto implements Model {
  private HashSet<PoliticalCommunity> politicalCommunities = new HashSet<>();
  private HashSet<PostalCommunity> postalCommunities = new HashSet<>();
  private HashSet<Canton> cantons = new HashSet<>();
  private HashSet<District> districts = new HashSet<>();
}
