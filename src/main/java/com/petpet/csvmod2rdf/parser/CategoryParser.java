package com.petpet.csvmod2rdf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

import com.petpet.csvmod2rdf.model.Category;
import com.petpet.csvmod2rdf.model.CriterionCategory;

public class CategoryParser extends CSVParser {

  @Override
  public void read() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.getFile()), ';');

      //skip header...
      String[] line = reader.readNext();
      String cCat = "";
      String cSubCat = "";
      String cSubSubCat = "";
      String name, scope;
      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {
          cCat = this.getCurrentCategory(cCat, line[0]);
          cSubCat = this.getCurrentCategory(cSubCat, line[1]);
          cSubSubCat = this.getCurrentCategory(cSubSubCat, line[2]);
          name = line[3];
          scope = line[4];

          Category cat = new Category(cCat, null);
          this.getCache().putCategory(cat);
          
          cat = new Category(cSubCat, cCat);
          this.getCache().putCategory(cat);
          
          cat = new Category(cSubSubCat, cSubCat);
          this.getCache().putCategory(cat);
          
          CriterionCategory critCat = new CriterionCategory(name, cSubSubCat);
          critCat.setScope(scope);
          this.getCache().putCategory(critCat);
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getCurrentCategory(String current, String active) {
    return active.equals("") ? current : active;
  }

}
