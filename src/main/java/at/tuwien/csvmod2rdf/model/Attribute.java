package at.tuwien.csvmod2rdf.model;

import java.util.ArrayList;
import java.util.List;

public class Attribute {

  private CriterionCategory category;
  
  private String id;
  
  private String name;
  
  private String description;
  
  private List<Measure> measures;
  
  public Attribute(String name, String description, CriterionCategory category) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.measures = new ArrayList<Measure>();
  }

  public CriterionCategory getCategory() {
    return category;
  }

  public void setCategory(CriterionCategory category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Measure> getMeasures() {
    return measures;
  }

  public void setMeasures(List<Measure> measures) {
    this.measures = measures;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
