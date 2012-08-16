package com.petpet.csvmod2rdf.model;

import java.util.List;

public class CriterionCategory {

  private String category;
  
  private String subcategory;
  
  private String subsubcategory;
  
  private String name;
  
  private String scope;
  
  private List<Attribute> attributes;
  
  public CriterionCategory() {
    
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(String subcategory) {
    this.subcategory = subcategory;
  }

  public String getSubsubcategory() {
    return subsubcategory;
  }

  public void setSubsubcategory(String subsubcategory) {
    this.subsubcategory = subsubcategory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }
  
  
}
