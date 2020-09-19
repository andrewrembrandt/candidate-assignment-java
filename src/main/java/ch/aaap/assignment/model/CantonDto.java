package ch.aaap.assignment.model;

import java.util.HashSet;
import lombok.Data;
import lombok.ToString;

@Data
public class CantonDto implements Canton {
  private final String code;
  private final String name;

  @ToString.Exclude private HashSet<District> districts = new HashSet<>();
}
