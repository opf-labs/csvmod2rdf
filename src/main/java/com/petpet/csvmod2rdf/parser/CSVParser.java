package com.petpet.csvmod2rdf.parser;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;
import com.petpet.csvmod2rdf.utils.Cache;

public abstract class CSVParser {

  private IntegrityCheckStrategy integrityChecker;
  
  private Cache cache;
  
  private String file;

  public IntegrityCheckStrategy getIntegrityChecker() {
    return integrityChecker;
  }

  public void setIntegrityChecker(IntegrityCheckStrategy integrityChecker) {
    this.integrityChecker = integrityChecker;
  }

  public Cache getCache() {
    return cache;
  }

  public void setCache(Cache cache) {
    this.cache = cache;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }
  
  public abstract void read();
}
