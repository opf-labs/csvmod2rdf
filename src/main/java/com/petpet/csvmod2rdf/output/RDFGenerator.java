package com.petpet.csvmod2rdf.output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.petpet.csvmod2rdf.interfaces.OutputGenerator;
import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.Category;
import com.petpet.csvmod2rdf.model.Measure;
import com.petpet.csvmod2rdf.utils.Cache;

public class RDFGenerator implements OutputGenerator {

  private Cache cache;

  public RDFGenerator(Cache cache) {
    this.cache = cache;
  }

  public String getOutput() {

    try {
      String stub = IOUtils.toString(new FileInputStream("src/main/resources/helper/stub.txt"));
      String catTmpl = IOUtils.toString(new FileInputStream("src/main/resources/helper/criterioncategory.txt"));
      String subcatTmpl = IOUtils.toString(new FileInputStream("src/main/resources/helper/category.txt"));
      String attTmpl = IOUtils.toString(new FileInputStream("src/main/resources/helper/attribute.txt"));
      String mTmpl = IOUtils.toString(new FileInputStream("src/main/resources/helper/measure.txt"));

      StringBuilder individuals = new StringBuilder();

      Map<String, Category> cats = this.cache.getCategories();

      for (String key : cats.keySet()) {
        Category category = cats.get(key);
        // generate top level categories

        String catInd;
        if (category.isLeaf()) {
          catInd = catTmpl.replace("@{about}",
              "http://scape-project.eu.pw/vocab/" + getIdFromString(category.getName())).replace("@{name}",
              category.getName());
        } else {
          // this is a category
          catInd = subcatTmpl.replace("@{about}", getIdFromString(category.getName())).replace("@{name}",
              category.getName());
          StringBuilder subcats = new StringBuilder();
          for (Category c : category.getChidlren()) {
            subcats.append("\n  <subcategory rdf:resource=\"http://scape-project.eu/pw/vocab/"
                + getIdFromString(c.getName()) + "\" />");
          }
          catInd = catInd.replace("@{subcategory}", subcats.toString());
        }

        individuals.append(catInd);
      }

      Map<String, Attribute> atts = this.cache.getAttributes();

      for (String key : atts.keySet()) {
        Attribute attribute = atts.get(key);
        String att = attTmpl
            .replace("@{about}", attribute.getId())
            .replace("@{name}", attribute.getName())
            .replace("@{description}", attribute.getDescription())
            .replace("@{category}",
                "http://scape-project.eu/pw/vocab/" + getIdFromString(attribute.getCategory().getName()));

        StringBuilder measures = new StringBuilder();
        StringBuilder mInd = new StringBuilder();
        for (Measure m : attribute.getMeasures()) {
          measures.append("\n  <measure rdf:resource=\"http://scape-project.eu/pw/vocab/" + m.getId() + "\" />");

          String measure = mTmpl.replace("@{about}", m.getId()).replace("@{name}", m.getName())
              .replace("@{description}", m.getDescription()).replace("@{attribute}", attribute.getId());
          mInd.append(measure);
        }

        att = att.replace("@{measures}", measures.toString());

        individuals.append(att);
        individuals.append(mInd.toString());
      }

      return stub.replace("@{body}", individuals.toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

  private String getIdFromString(String str) {
    return str.toLowerCase().replace(" ", "_").replace(":", "_");
  }

}
