package at.tuwien.csvmod2rdf.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Category;

public class Cache {

  private Map<String, Category> categories;

  private Map<String, Attribute> attributes;

  public Cache() {
    this.categories = Collections.synchronizedMap(new HashMap<String, Category>());
    this.attributes = Collections.synchronizedMap(new HashMap<String, Attribute>());
  }

  public void putCategory(Category cat) {
    if (cat != null) {

      Category original = this.categories.get(cat.getName());
      if (original == null) {
        // not existing
        if (cat.getParent() == null) {
          // top category
          this.categories.put(cat.getName(), cat);

        } else {
          // child category
          Category parent = this.categories.get(cat.getParent());
          parent.getChidlren().add(cat);
          this.categories.put(cat.getName(), cat);
        }
      }
    }
  }

  public void putAttribute(Attribute attr) {
    if (attr != null) {
      this.attributes.put(attr.getName(), attr);
    }
  }

  public Category getCategory(String name) {
    return this.categories.get(name);
  }

  public Attribute getAttribute(String name) {
    return this.attributes.get(name);
  }

  public Map<String, Category> getCategories() {
    return Collections.unmodifiableMap(categories);
  }

  public void setCategories(Map<String, Category> categories) {
    this.categories = categories;
  }

  public Map<String, Attribute> getAttributes() {
    return Collections.unmodifiableMap(attributes);
  }

  public void setAttributes(Map<String, Attribute> attributes) {
    this.attributes = attributes;
  }
}
