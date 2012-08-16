package com.petpet.csvmod2rdf.parser;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;

public class CategoryIntegrityChecker implements IntegrityCheckStrategy {

  public boolean examine(String[] line) {
    boolean proceed = true;
    if (line.length != 5) {
      proceed = false;
    }
    
    if (line[3] == null || line[3].equals("")) {
      proceed = false;
    }
    
    return proceed;
  }
}
