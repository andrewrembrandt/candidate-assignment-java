package ch.aaap.assignment.model;

import java.util.Set;

public interface District {

  public String getNumber();

  public String getName();

  public Canton getCanton();

  public Set<PoliticalCommunity> getPoliticalCommunities();

  public Set<PostalCommunity> getPostalCommunities();
}
