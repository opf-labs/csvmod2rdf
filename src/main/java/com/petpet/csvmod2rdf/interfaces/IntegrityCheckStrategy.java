package com.petpet.csvmod2rdf.interfaces;

public interface IntegrityCheckStrategy {

  boolean examine(String[] line);
}
