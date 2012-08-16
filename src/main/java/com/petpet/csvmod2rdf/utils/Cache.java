package com.petpet.csvmod2rdf.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.CriterionCategory;

public class Cache {

  private Map<String, CriterionCategory> categories;

  private Map<String, Attribute> attributes;

  public Cache() {
    this.categories = Collections.synchronizedMap(new HashMap<String, CriterionCategory>());
    this.attributes = Collections.synchronizedMap(new HashMap<String, Attribute>());
  }
  
  public void putCategory(CriterionCategory cat) {
    if (cat != null) {
      this.categories.put(cat.getName(), cat);
    }
  }
  
  public void putAttribute(Attribute attr) {
    if (attr != null) {
      this.attributes.put(attr.getName(), attr);
    }
  }
  
  public CriterionCategory getCategory(String name) {
    return this.categories.get(name);
  }
  
  public Attribute getAttribute(String name) {
    return this.attributes.get(name);
  }

  public Map<String, CriterionCategory> getCategories() {
    return Collections.unmodifiableMap(categories);
  }

  public void setCategories(Map<String, CriterionCategory> categories) {
    this.categories = categories;
  }

  public Map<String, Attribute> getAttributes() {
    return Collections.unmodifiableMap(attributes);
  }

  public void setAttributes(Map<String, Attribute> attributes) {
    this.attributes = attributes;
  }
}
