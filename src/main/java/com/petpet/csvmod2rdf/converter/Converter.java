package com.petpet.csvmod2rdf.converter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.CriterionCategory;
import com.petpet.csvmod2rdf.model.Measure;
import com.petpet.csvmod2rdf.utils.Cache;

public class Converter {

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
    try {
      CSVReader reader = new CSVReader(new FileReader(this.categoriesPath));

      String[] line;
      String cCat = "";
      String cSubCat = "";
      String cSubSubCat = "";
      String name, scope;
      while ((line = reader.readNext()) != null) {
        cCat = this.getCurrentCategory(cCat, line[0]);
        cSubCat = this.getCurrentCategory(cSubCat, line[1]);
        cSubSubCat = this.getCurrentCategory(cSubSubCat, line[2]);
        name = line[3];
        scope = line[4];

        if (name != null && !name.equals("")) {
          CriterionCategory cat = new CriterionCategory();
          cat.setName(name);
          cat.setScope(scope);
          cat.setCategory(cCat);
          cat.setSubcategory(cSubCat);
          cat.setSubsubcategory(cSubSubCat);
          this.cache.putCategory(cat);
        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readAttributes() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.attributesPath));
      
      String[] line;
      String id, cat, name, desc;
      while ((line = reader.readNext()) != null) {
        id = line[0];
        cat = line[1];
        name = line[2];
        desc = line[3];

        if (id != null && !id.equals("")) {
          CriterionCategory category = this.cache.getCategory(cat);
          
          //TODO check category for null...
          Attribute attr = new Attribute(name, desc, category);
          this.cache.putAttribute(attr);
          
        } else {
          System.out.println("An error occurred wile reading line: " + Arrays.deepToString(line));
        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readMeasures() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.measuresPath));

      String[] line;
      String id, attr, name, desc, scale, restriction, deprecated;
      while ((line = reader.readNext()) != null) {
        id = line[0];
        attr = line[1];
        name = line[2];
        desc = line[3];
        scale = line[4];
        restriction = line[5];
        deprecated = line[6];

        if (id != null && !id.equals("")) {
          Attribute a = this.cache.getAttribute(attr);
          if (a != null) {
          Measure m = new Measure(name, desc, a);
          m.setScale(scale);
          m.setRestriction(restriction);
          //TODO set deprecated...
          
          a.getMeasures().add(m);
          this.cache.putAttribute(a);
          } else {
            System.out.println("No attribute with that name was found: " + attr);
          }
          
        } else {
          System.out.println("An error occurred wile reading line: " + Arrays.deepToString(line));
        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

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
    
  }

  private String getCurrentCategory(String current, String active) {
    return active.equals("") ? current : active;
  }

}
