package com.petpet.csvmod2rdf.model;

public class Measure {

  private Attribute attribute;
  
  private String name;
  
  private String description;
  
  private String scale;
  
  private String restriction;
  
  private String deprecated;
  
  public Measure(String name, String description, Attribute attribute) {
    this.name = name;
    this.description = description;
    this.attribute = attribute;
  }

  public Attribute getAttribute() {
    return attribute;
  }

  public void setAttribute(Attribute attribute) {
    this.attribute = attribute;
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

  public String getScale() {
    return scale;
  }

  public void setScale(String scale) {
    this.scale = scale;
  }

  public String getRestriction() {
    return restriction;
  }

  public void setRestriction(String restriction) {
    this.restriction = restriction;
  }

  public String getDeprecated() {
    return deprecated;
  }

  public void setDeprecated(String deprecated) {
    this.deprecated = deprecated;
  }
}
