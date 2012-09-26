package com.petpet.csvmod2rdf.common;

public class Constants {

  public static final String NAME = "csvmod2rdf";

  public static final String VERSION = "0.3.1";

  public static final String SIGNATURE = "<!-- Generated with " + NAME + " " + VERSION + "  -->";
  
  
  //----- templates ------
  
  public static final String ABOUT_KEY = "@{about}";
  
  public static final String BODY_KEY = "@{body}";
  
  public static final String NAME_KEY = "@{name}";
  
  public static final String DESCRIPTION_KEY = "@{description}";
  
  public static final String CATEGORY_KEY = "@{category}";
  
  public static final String MEASURES_KEY = "@{measures}";
  
  public static final String ATTRIBUTE_KEY = "@{attribute}";
  
  public static final String SUBCATEGORIES_KEY = "@{subcategories}";

  public static final String SCALE_KEY = "@{scale}";
  
  public static final String RESTRICTION_KEY = "@{restriction}";
  
  public static final String RESTRICTION_LINE = "  <restriction>" + RESTRICTION_KEY + "</restriction>";

  public static final String SCOPE_KEY = "@{scope}";

  public static final String ID_KEY = "@{id}";

  public static final String DEFAULT_NS = "http://scape-project.eu.pw/vocab/";

}
