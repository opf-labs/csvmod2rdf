package at.tuwien.csvmod2rdf.model;

import java.util.List;

public class CriterionCategory extends Category {

  public CriterionCategory(String name, String parent) {
    super(name, parent);
    this.setLeaf(true);
  }

  private String scope;
  
  private List<Attribute> attributes;
  
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
