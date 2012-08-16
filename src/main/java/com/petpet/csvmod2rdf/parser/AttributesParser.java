package com.petpet.csvmod2rdf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.CriterionCategory;

public class AttributesParser extends CSVParser {

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

          CriterionCategory category = this.getCache().getCategory(cat);

          Attribute attr = new Attribute(name, desc, category);
          attr.setId(id);
          this.getCache().putAttribute(attr);

        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
