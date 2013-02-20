package at.tuwien.csvmod2rdf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Category;
import at.tuwien.csvmod2rdf.model.CriterionCategory;
import au.com.bytecode.opencsv.CSVReader;


public class AttributesParser extends CSVParser {
  
  private static final Logger LOG = LoggerFactory.getLogger(AttributesParser.class);

  @Override
  public void read() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.getFile()), ';');

      // skip header
      String[] line = reader.readNext();
      String id, cat, name, desc;
      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {
          id = line[0];
          cat = line[1];
          name = line[2];
          desc = line[3];

          Category category = this.getCache().getCategory(cat);

          if (category.isLeaf()) {
            Attribute attr = new Attribute(name, desc, (CriterionCategory) category);
            attr.setId(id);
            this.getCache().putAttribute(attr);
          } else {
            LOG.warn("There must be a collision of category names for [{}]", category.getName());
            
          }

        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
