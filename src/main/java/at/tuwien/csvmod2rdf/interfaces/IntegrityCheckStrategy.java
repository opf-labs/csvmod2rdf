package at.tuwien.csvmod2rdf.interfaces;

public interface IntegrityCheckStrategy {

  boolean examine(String[] line);
}
