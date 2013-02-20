package at.tuwien.csvmod2rdf.model;

public class Measure {

  private Attribute attribute;
  
  private String id;
  
  private String name;
  
  private String description;
  
  private String scale;
  
  private String restriction;
  
  private String deprecated;
  
  private String oldPropertyUri;
  
  public Measure(String id, String name, String description, Attribute attribute) {
    this.id = id;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

public String getOldPropertyUri() {
	return oldPropertyUri;
}

public void setOldPropertyUri(String oldPropertyUri) {
	this.oldPropertyUri = oldPropertyUri;
}
}
