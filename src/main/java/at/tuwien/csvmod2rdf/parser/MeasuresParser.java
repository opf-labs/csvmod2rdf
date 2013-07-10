package at.tuwien.csvmod2rdf.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Measure;
import au.com.bytecode.opencsv.CSVReader;


public class MeasuresParser extends CSVParser {

  @Override
  public void read() {
    try {
      CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(this.getFile()), Charset.forName("UTF-8").newDecoder()), ';');
      //skip header
      String[] line = reader.readNext();
      String id, attr, name, desc, scale, restriction, deprecated, oldPropertyUri;
      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {
          id = line[0].trim();
          attr = line[1].trim();
          name = line[2].trim();
          desc = line[3].trim();
          scale = line[4].trim();
          restriction = line[5].trim();
          deprecated = line[6].trim();
          oldPropertyUri = line[7].trim();

          Attribute a = this.getCache().getAttribute(attr);
          Measure m = new Measure(id, name, desc, a);
          m.setScale(scale);
          m.setRestriction(restriction);
          m.setDeprecated(deprecated);

          List<Measure> measures = a.getMeasures();
          measures.add(m);
          a.setMeasures(measures);
          m.setOldPropertyUri(oldPropertyUri);
          this.getCache().putAttribute(a);

        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
