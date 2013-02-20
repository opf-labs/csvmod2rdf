package at.tuwien.csvmod2rdf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import at.tuwien.csvmod2rdf.model.Category;
import at.tuwien.csvmod2rdf.model.CriterionCategory;
import au.com.bytecode.opencsv.CSVReader;


public class CategoryParser extends CSVParser {

  @Override
  public void read() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.getFile()), ';');

      // skip header...
      String[] line = reader.readNext();

      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {

          Category cat = new Category(line[0], null);
          this.getCache().putCategory(cat);

          if (line[1] != null && !line[1].equals("")) {
            cat = new Category(line[1], line[0]);
            this.getCache().putCategory(cat);
          }

          if (line[2] != null && !line[2].equals("")) {
            cat = new Category(line[2], line[1]);
            this.getCache().putCategory(cat);
          }

          CriterionCategory critCat = new CriterionCategory(line[3], cat.getName());
          critCat.setScope(line[4]);
          this.getCache().putCategory(critCat);
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
