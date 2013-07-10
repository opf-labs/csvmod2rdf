package at.tuwien.csvmod2rdf.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import at.tuwien.csvmod2rdf.model.Category;
import at.tuwien.csvmod2rdf.model.CriterionCategory;
import au.com.bytecode.opencsv.CSVReader;


public class CategoryParser extends CSVParser {

  @Override
  public void read() {
    try {
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(this.getFile()), Charset.forName("UTF-8").newDecoder()), ';');

      // skip header...
      String[] line = reader.readNext();

      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {
           String col0 = line[0].trim();
           String col1 = (null == line[1])? "" : line[1].trim();
           String col2 = (null == line[2])? "" : line[2].trim();

          Category cat = new Category(col0, null);
          this.getCache().putCategory(cat);

          if (!"".equals(col1)) {
            cat = new Category(col1, col0);
            this.getCache().putCategory(cat);
          }

          if (!"".equals(col2)) {
            cat = new Category(col2, col1);
            this.getCache().putCategory(cat);
          }

          CriterionCategory critCat = new CriterionCategory(line[3].trim(), cat.getName());
          critCat.setScope(line[4].trim());
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
