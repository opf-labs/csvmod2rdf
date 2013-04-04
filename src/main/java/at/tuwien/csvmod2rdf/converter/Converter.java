package at.tuwien.csvmod2rdf.converter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tuwien.csvmod2rdf.interfaces.OutputGenerator;
import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Measure;
import at.tuwien.csvmod2rdf.output.FileOutput;
import at.tuwien.csvmod2rdf.output.RDFXMLGenerator;
import at.tuwien.csvmod2rdf.output.XSLTRuleGenerator;
import at.tuwien.csvmod2rdf.parser.AttributeIntegrityChecker;
import at.tuwien.csvmod2rdf.parser.AttributesParser;
import at.tuwien.csvmod2rdf.parser.CSVParser;
import at.tuwien.csvmod2rdf.parser.CategoryIntegrityChecker;
import at.tuwien.csvmod2rdf.parser.CategoryParser;
import at.tuwien.csvmod2rdf.parser.MeasuresIntegrityChecker;
import at.tuwien.csvmod2rdf.parser.MeasuresParser;
import at.tuwien.csvmod2rdf.utils.Cache;

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
    
    try {
        FileWriter categoriesWriter = new FileWriter("quality_categories.rdf");
        FileWriter attributesWriter = new FileWriter("quality_attributes.rdf");
        FileWriter measuresWriter = new FileWriter("quality_measures.rdf");
        
        OutputGenerator gen = new RDFXMLGenerator(this.cache, categoriesWriter, attributesWriter, measuresWriter);
        String rdf = gen.getOutput();
        categoriesWriter.close();
        attributesWriter.close();
        measuresWriter.close();
        
//        System.out.println("################# OUTPUT #################");
//        System.out.println(rdf);
//        System.out.println("################# END #################");
        
//        FileOutput output = new FileOutput();
//        output.store(rdf, "all_individuals.rdf");
        
        gen = new XSLTRuleGenerator(this.cache);
        String xslt = gen.getOutput();
        FileOutput output = new FileOutput();
        output.store(xslt, "xslt_rules.snippet");
    } catch (IOException e) {
        e.printStackTrace();
    }    
    
  }

}
