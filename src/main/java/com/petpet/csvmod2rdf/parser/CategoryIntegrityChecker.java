package com.petpet.csvmod2rdf.parser;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;

public class CategoryIntegrityChecker implements IntegrityCheckStrategy {
  
  private static final Logger LOG = LoggerFactory.getLogger(CategoryIntegrityChecker.class);

  public boolean examine(String[] line) {
    LOG.debug("Examine line: [{}]", Arrays.deepToString(line));
    boolean proceed = true;
    if (line.length != 5) {
      LOG.warn("Line has {} columns instead of 5. File: categories, line: [{}]", line.length, Arrays.deepToString(line));
      proceed = false;
    }
    
    if (line[3] == null || line[3].equals("")) {
      LOG.warn("No name provided for category. File: categories, line: [{}]", Arrays.deepToString(line));
      proceed = false;
    }
    
    return proceed;
  }
}
