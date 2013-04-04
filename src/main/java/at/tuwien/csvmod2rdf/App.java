package at.tuwien.csvmod2rdf;

import at.tuwien.csvmod2rdf.converter.Converter;

/**
 * Entry point
 * 
 */
public class App {

  public static void main(String... args) {
    if (args.length != 3) {
      System.err.println("Wrong number of arguments!");
      System.err
          .println("Usage: java -jar csvmod2rdf <path/to/qualitymod.csv> <path/to/attributes.csv> <path/to/measures.csv>");
      System.exit(1);
    }
    
    Converter converter = new Converter(args[0], args[1], args[2]);
    converter.execute();
  }
}
