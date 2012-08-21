package com.petpet.csvmod2rdf.output;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOutput {

  private static final Logger LOG = LoggerFactory.getLogger(FileOutput.class);
  
  public void store(String rdf, String file) {
    if (rdf == null) {
      LOG.error("Passed rdf string is null, skipping storage");
      return;
    }
    
    if (file == null || file.equals("")) {
      file = "output.rdf";
    }
    
    try {
      FileWriter writer = new FileWriter(file);
      writer.append(rdf);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      LOG.error("An error occurred: {}", e.getMessage());
    }
  }
}
