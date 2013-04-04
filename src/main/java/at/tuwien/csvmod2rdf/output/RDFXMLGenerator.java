package at.tuwien.csvmod2rdf.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import at.tuwien.csvmod2rdf.common.Constants;
import at.tuwien.csvmod2rdf.interfaces.OutputGenerator;
import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Category;
import at.tuwien.csvmod2rdf.model.CriterionCategory;
import at.tuwien.csvmod2rdf.model.Measure;
import at.tuwien.csvmod2rdf.utils.Cache;

public class RDFXMLGenerator implements OutputGenerator {

    private Cache cache;
    
    private Writer categoriesWriter;
    private Writer attributesWriter;
    private Writer measuresWriter;
    
    public RDFXMLGenerator(Cache cache, Writer categoriesWriter, Writer attributesWriter, Writer measuresWriter) {
        this.cache = cache;
        this.categoriesWriter = categoriesWriter;
        this.attributesWriter = attributesWriter;
        this.measuresWriter = measuresWriter;
    }


    public String getOutput() {

        try {

            String stub = IOUtils.toString(getClass().getResourceAsStream("/helper/stub.txt"));
            String catTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/criterioncategory.txt"));
            String subcatsTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/category.txt"));
            String subcatTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/subcategory.txt"));
            String attTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/attributes.txt"));
            String msTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/measures.txt"));
            String mTmpl = IOUtils.toString(getClass().getResourceAsStream("/helper/measure.txt"));

            StringBuilder categories = new StringBuilder();

            Map<String, Category> cats = this.cache.getCategories();

            for (String key : cats.keySet()) {
                // generate top level categories
                Category category = cats.get(key);

                String catInd;
                if (category.isLeaf()) {
                    // this is a criterion category
                    catInd = catTmpl.replace(Constants.ABOUT_KEY, getIdFromString(category.getName()))
                        .replace(Constants.NAME_KEY, category.getName())
                        .replace(Constants.SCOPE_KEY, this.getScopeId(((CriterionCategory) category).getScope()));
                } else {
                    // this is a category
                    catInd = subcatsTmpl.replace(Constants.ABOUT_KEY, getIdFromString(category.getName())).replace(
                        Constants.NAME_KEY, category.getName());

                    StringBuilder subcats = new StringBuilder();
                    for (Category c : category.getChidlren()) {
                        subcats.append("\n" + subcatTmpl.replace(Constants.ID_KEY, getIdFromString(c.getName())));
                    }
                    catInd = catInd.replace(Constants.SUBCATEGORIES_KEY, subcats.toString());
                }

                categories.append(catInd);
            }
            this.categoriesWriter.append(stub.replace(Constants.BODY_KEY, categories.toString()));
            this.categoriesWriter.append(Constants.SIGNATURE);

            Map<String, Attribute> atts = this.cache.getAttributes();
            StringBuilder attributes = new StringBuilder();
            StringBuilder measures = new StringBuilder();
            
            for (String key : atts.keySet()) {
                // generate attributes
                Attribute attribute = atts.get(key);
                String att = attTmpl.replace(Constants.ABOUT_KEY, attribute.getId())
                    .replace(Constants.NAME_KEY, attribute.getName())
                    .replace(Constants.DESCRIPTION_KEY, attribute.getDescription())
                    .replace(Constants.CATEGORY_KEY, getIdFromString(attribute.getCategory().getName()));

                StringBuilder measuresRefs = new StringBuilder();

                for (Measure m : attribute.getMeasures()) {
                    // generate measures

                    measuresRefs.append("\n" + mTmpl.replace(Constants.ID_KEY, m.getId()));

                    String measure = msTmpl.replace(Constants.ABOUT_KEY, m.getId())
                        .replace(Constants.NAME_KEY, m.getName()).replace(Constants.ATTRIBUTE_KEY, attribute.getId())
                        .replace(Constants.DESCRIPTION_KEY, m.getDescription())
                        .replace(Constants.SCALE_KEY, this.getScaleId(m.getScale()));

                    if (m.getRestriction() != null && !m.getRestriction().equals("")) {
                        measure = measure.replace(Constants.RESTRICTION_KEY, m.getRestriction());
                    } else {
                        measure = measure.replace("\n" + Constants.RESTRICTION_LINE, "");
                    }

                    measures.append(measure);
                }

                att = att.replace(Constants.MEASURES_KEY, measuresRefs.toString());
                attributes.append(att);
            }
            this.attributesWriter.append(stub.replace(Constants.BODY_KEY, attributes.toString()));
            this.attributesWriter.append(Constants.SIGNATURE);
            
            this.measuresWriter.append(stub.replace(Constants.BODY_KEY, measures.toString()));
            this.measuresWriter.append(Constants.SIGNATURE);

            // and create one string for all individuals
            categories.append(attributes).append(measures).append(Constants.SIGNATURE);
            return stub.replace(Constants.BODY_KEY, categories.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String getIdFromString(String str) {
        return str.toLowerCase().replace(" ", "_").replace(":", "_").replace("__", "_");
    }

    private String getScaleId(String scale) {
        return scale.toUpperCase().replace(" ", "");
    }

    private String getScopeId(String scope) {
        return scope.toUpperCase().replace(" ", "");
    }

}
