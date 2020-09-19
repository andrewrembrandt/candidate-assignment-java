package ch.aaap.assignment;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import java.util.HashMap;
import java.util.Set;
import lombok.val;

public class ModelBuilder {
  /**
   * Construct the related Political and Postal Community model from input CSV data
   *
   * @param csvPoliticalCommunities of the extracted CSV file data
   * @param csvPostalCommunities of the extracted CSV file data
   * @return community model
   */
  public static Model build(
      Set<CSVPoliticalCommunity> csvPoliticalCommunities,
      Set<CSVPostalCommunity> csvPostalCommunities) {

    val model = new ModelDto();
    val cantonMap = new HashMap<String, CantonDto>();
    val districtMap = new HashMap<String, DistrictDto>();
    val polCmmtyMap = new HashMap<String, PoliticalCommunityDto>();
    val postCmmtyMap = new HashMap<String, PostalCommunityDto>();

    for (CSVPoliticalCommunity csvPoliticalCommty : csvPoliticalCommunities) {
      Canton canton =
          cantonMap.computeIfAbsent(
              csvPoliticalCommty.getCantonCode(),
              cc -> {
                val newCanton = new CantonDto(cc, csvPoliticalCommty.getCantonName());
                model.getCantons().add(newCanton);
                return newCanton;
              });

      var district = districtMap.get(csvPoliticalCommty.getDistrictNumber());
      if (district == null) {
        district =
            new DistrictDto(
                csvPoliticalCommty.getDistrictNumber(),
                csvPoliticalCommty.getDistrictName(),
                canton);
        districtMap.put(csvPoliticalCommty.getDistrictNumber(), district);
        canton.getDistricts().add(district);

        model.getDistricts().add(district);
      }

      val polCmmty =
          new PoliticalCommunityDto(
              csvPoliticalCommty.getNumber(),
              csvPoliticalCommty.getName(),
              csvPoliticalCommty.getShortName(),
              csvPoliticalCommty.getLastUpdate(),
              district);
      district.getPoliticalCommunities().add(polCmmty);
      polCmmtyMap.put(csvPoliticalCommty.getNumber(), polCmmty);
      model.getPoliticalCommunities().add(polCmmty);
    }

    for (CSVPostalCommunity csvPostCmmty : csvPostalCommunities) {
      val canton = cantonMap.get(csvPostCmmty.getCantonCode());
      val polCmmty = polCmmtyMap.get(csvPostCmmty.getPoliticalCommunityNumber());
      val postCmmtyKey = csvPostCmmty.getZipCode() + csvPostCmmty.getZipCodeAddition();
      val postCmmty =
          postCmmtyMap.computeIfAbsent(
              postCmmtyKey,
              pck ->
                  new PostalCommunityDto(
                      csvPostCmmty.getZipCode(),
                      csvPostCmmty.getZipCodeAddition(),
                      csvPostCmmty.getName(),
                      canton));
      postCmmty.getPoliticalCommunities().add(polCmmty);
      polCmmty.getPostalCommunities().add(postCmmty);
      polCmmty.getDistrict().getPostalCommunities().add(postCmmty);
      model.getPostalCommunities().add(postCmmty);
    }

    return model;
  }
}
