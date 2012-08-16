package com.petpet.csvmod2rdf.parser;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petpet.csvmod2rdf.interfaces.IntegrityCheckStrategy;
import com.petpet.csvmod2rdf.utils.Cache;

public class AttributeIntegrityChecker implements IntegrityCheckStrategy {

  private static final Logger LOG = LoggerFactory.getLogger(AttributeIntegrityChecker.class);

  private Cache cache;

  public AttributeIntegrityChecker(Cache cache) {
    this.cache = cache;
  }

  public boolean examine(String[] line) {
    LOG.debug("Examine line [{}]", Arrays.deepToString(line));
    boolean proceed = true;

    if (line.length != 4) {
      LOG.warn("Line has {} columns instead of 4. File: attributes, line: [{}]", line.length, Arrays.deepToString(line));
      proceed = false;
    }

    if (line[0] == null || line[0].equals("")) {
      LOG.warn("No id provided. File: attributes, line: [{}]", Arrays.deepToString(line));
      proceed = false;
    }

    if (this.cache.getCategory(line[1]) == null) {
      LOG.warn("Category '{}' does not exist. File: attributes, line: [{}]", line[1], Arrays.deepToString(line));
      proceed = false;
    }

    return proceed;
  }

}
