package com.petpet.csvmod2rdf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.petpet.csvmod2rdf.model.Attribute;
import com.petpet.csvmod2rdf.model.Measure;

public class MeasuresParser extends CSVParser {

  @Override
  public void read() {
    try {
      CSVReader reader = new CSVReader(new FileReader(this.getFile()), ';');
      //skip header
      String[] line = reader.readNext();
      String id, attr, name, desc, scale, restriction, deprecated;
      while ((line = reader.readNext()) != null) {
        if (this.getIntegrityChecker().examine(line)) {
          id = line[0];
          attr = line[1];
          name = line[2];
          desc = line[3];
          scale = line[4];
          restriction = line[5];
          deprecated = line[6];

          Attribute a = this.getCache().getAttribute(attr);
          Measure m = new Measure(name, desc, a);
          m.setScale(scale);
          m.setRestriction(restriction);
          // TODO set deprecated...

          List<Measure> measures = a.getMeasures();
          measures.add(m);
          a.setMeasures(measures);
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
