package ch.aaap.assignment;

import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  private Model model = null;

  public Application() {
    initModel();
  }

  public static void main(String[] args) {
    new Application();
  }

  /** Reads the CSVs and initializes a in memory model */
  private void initModel() {
    Set<CSVPoliticalCommunity> politicalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> postalCommunities = CSVUtil.getPostalCommunities();

    model = ModelBuilder.build(politicalCommunities, postalCommunities);
  }
  /** @return model */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    return model.getCantons().stream()
        .filter(c -> c.getCode().equals(cantonCode))
        .findFirst()
        .map(
            canton ->
                canton.getDistricts().stream()
                    .mapToLong(d -> d.getPoliticalCommunities().size())
                    .sum())
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    return model.getCantons().stream()
        .filter(c -> c.getCode().equals(cantonCode))
        .findFirst()
        .map(canton -> (long) canton.getDistricts().size())
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    return model.getDistricts().stream()
        .filter(d -> d.getNumber().equals(districtNumber))
        .findFirst()
        .map(district -> (long) district.getPoliticalCommunities().size())
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return model.getPostalCommunities().stream()
        .filter(pc -> pc.getZipCode().equals(zipCode))
        .flatMap(
            postCmmmty ->
                postCmmmty.getPoliticalCommunities().stream().map(pc -> pc.getDistrict().getName()))
        .collect(Collectors.toSet());
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    return model.getPostalCommunities().stream()
        .filter(pc -> pc.getName().equals(postalCommunityName))
        .findFirst()
        .flatMap(
            postCmmty ->
                postCmmty.getPoliticalCommunities().stream()
                    .map(PoliticalCommunity::getLastUpdate)
                    .max(
                        LocalDate
                            ::compareTo)) // Take most recent update from the collection of
                                          // political communities
        .orElseThrow(IllegalArgumentException::new);
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    return model.getPoliticalCommunities().stream()
        .filter(pc -> pc.getPostalCommunities().isEmpty())
        .count();
  }
}
