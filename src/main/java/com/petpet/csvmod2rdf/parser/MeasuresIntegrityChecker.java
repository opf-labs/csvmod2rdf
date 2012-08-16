package com.petpet.csvmod2rdf.parser;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;
import com.petpet.csvmod2rdf.utils.Cache;

public class MeasuresIntegrityChecker implements IntegrityCheckStrategy {

  private Cache cache;

  public MeasuresIntegrityChecker(Cache cache) {
    this.cache = cache;
  }

  public boolean examine(String[] line) {
    boolean proceed = false;

    // with OLD PROPERTY
    if (line.length != 8) {
      proceed = false;
    }

    if (line[0] == null || line[0].equals("")) {
      proceed = false;
    }

    if (this.cache.getAttribute(line[1]) == null) {
      proceed = false;
    }

    return proceed;
  }

}
