package at.tuwien.csvmod2rdf.parser;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tuwien.csvmod2rdf.interfaces.IntegrityCheckStrategy;
import at.tuwien.csvmod2rdf.utils.Cache;

public class MeasuresIntegrityChecker implements IntegrityCheckStrategy {
  
  private static final Logger LOG = LoggerFactory.getLogger(MeasuresIntegrityChecker.class);

  private Cache cache;

  public MeasuresIntegrityChecker(Cache cache) {
    this.cache = cache;
  }

  public boolean examine(String[] line) {
    LOG.debug("Examine line [{}]", Arrays.deepToString(line));
    boolean proceed = true;

    // with OLD PROPERTY
    // remove comments
    if (line.length != 8) {
      LOG.warn("Line has {} columns instead of 8. File: measures, line: [{}]", line.length, Arrays.deepToString(line));
      proceed = false;
    }

    if (line[0] == null || line[0].equals("")) {
      LOG.warn("No id provided. File: measures, line: [{}]", line);
      proceed = false;
    }

    if (this.cache.getAttribute(line[1]) == null) {
      LOG.warn("Attribute '{}' does not exist. File: measures, line: [{}]", line[1], Arrays.deepToString(line));
      proceed = false;
    }

    return proceed;
  }

}
