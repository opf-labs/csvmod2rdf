package com.petpet.csvmod2rdf.converter;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petpet.csvmod2rdf.interfaces.OutputGenerator;
import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.Measure;
import com.petpet.csvmod2rdf.output.RDFGenerator;
import com.petpet.csvmod2rdf.parser.AttributeIntegrityChecker;
import com.petpet.csvmod2rdf.parser.AttributesParser;
import com.petpet.csvmod2rdf.parser.CSVParser;
import com.petpet.csvmod2rdf.parser.CategoryIntegrityChecker;
import com.petpet.csvmod2rdf.parser.CategoryParser;
import com.petpet.csvmod2rdf.parser.MeasuresIntegrityChecker;
import com.petpet.csvmod2rdf.parser.MeasuresParser;
import com.petpet.csvmod2rdf.utils.Cache;

public class Converter {

  private static final Logger LOG = LoggerFactory.getLogger(Converter.class);

  private Cache cache;
  private String categoriesPath;
  private String attributesPath;
  private String measuresPath;

  /**
   * Creates a simple converter with the paths to each of the csv file.
   * 
   * @param s1
   *          the quality model csv file
   * @param s2
   *          the attributes csv file
   * @param s3
   *          the measures csv file
   */
  public Converter(String s1, String s2, String s3) {
    this.categoriesPath = s1;
    this.attributesPath = s2;
    this.measuresPath = s3;
    this.cache = new Cache();
  }

  public void execute() {
    this.readCategories();
    this.readAttributes();
    this.readMeasures();
    this.convert();

  }

  private void readCategories() {
    LOG.info("Reading categories file");
    CSVParser cat = new CategoryParser();
    cat.setFile(this.categoriesPath);
    cat.setCache(this.cache);
    cat.setIntegrityChecker(new CategoryIntegrityChecker());
    cat.read();
  }

  private void readAttributes() {
    LOG.info("Reading attributes file");
    CSVParser att = new AttributesParser();
    att.setFile(this.attributesPath);
    att.setCache(this.cache);
    att.setIntegrityChecker(new AttributeIntegrityChecker(this.cache));
    att.read();
  }

  private void readMeasures() {
    LOG.info("Reading measures file");
    CSVParser mp = new MeasuresParser();
    mp.setFile(this.measuresPath);
    mp.setCache(this.cache);
    mp.setIntegrityChecker(new MeasuresIntegrityChecker(this.cache));
    mp.read();

  }

  private void convert() {
    int cat = this.cache.getCategories().values().size();
    int attr = this.cache.getAttributes().values().size();
    int meas = 0;

    Map<String, Attribute> attributes = this.cache.getAttributes();
    for (String key : attributes.keySet()) {
      List<Measure> measures = attributes.get(key).getMeasures();
      if (measures != null) {
        meas += measures.size();
      }
    }

    System.out.println("Parsed " + cat + " categories, " + attr + " attributes and " + meas + " measures");

    OutputGenerator gen = new RDFGenerator(this.cache);
    System.out.println(gen.getOutput());
  }

}
