package com.petpet.csvmod2rdf.parser;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;
import com.petpet.csvmod2rdf.utils.Cache;

public class AttributeIntegrityChecker implements IntegrityCheckStrategy {

  private Cache cache;

  public AttributeIntegrityChecker(Cache cache) {
    this.cache = cache;
  }

  public boolean examine(String[] line) {
    boolean proceed = true;

    if (line.length != 4) {
      proceed = false;
    }

    if (line[0] == null || line[0].equals("")) {
      proceed = false;
    }

    if (this.cache.getCategory(line[1]) == null) {
      proceed = false;
    }

    return proceed;
  }

}
