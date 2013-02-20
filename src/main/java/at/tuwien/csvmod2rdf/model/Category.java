package at.tuwien.csvmod2rdf.model;

import java.util.HashSet;
import java.util.Set;

public class Category {

  private String name;
  
  private String parent;
  
  private Set<Category> chidlren;
  
  private boolean leaf;
  
  public Category(String name, String parent) {
    this.name = name;
    this.parent = parent;
    this.leaf = false;
    this.chidlren = new HashSet<Category>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public Set<Category> getChidlren() {
    return chidlren;
  }

  public void setChidlren(Set<Category> chidlren) {
    this.chidlren = chidlren;
  }

  public boolean isLeaf() {
    return leaf;
  }

  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (leaf ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Category other = (Category) obj;
    if (leaf != other.leaf)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    return true;
  }
  
  
}
