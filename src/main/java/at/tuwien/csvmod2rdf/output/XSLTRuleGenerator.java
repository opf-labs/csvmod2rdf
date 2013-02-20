package at.tuwien.csvmod2rdf.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import at.tuwien.csvmod2rdf.common.Constants;
import at.tuwien.csvmod2rdf.interfaces.OutputGenerator;
import at.tuwien.csvmod2rdf.model.Attribute;
import at.tuwien.csvmod2rdf.model.Measure;
import at.tuwien.csvmod2rdf.utils.Cache;

public class XSLTRuleGenerator implements OutputGenerator {

	private Cache cache;

	public XSLTRuleGenerator(Cache cache) {
		this.cache = cache;
	}

	public String getOutput() {

		try {
			StringBuilder rules = new StringBuilder();

			String ruleTmpl = IOUtils.toString(getClass().getResourceAsStream(
					"/helper/rule.txt"));

			Map<String, Attribute> atts = this.cache.getAttributes();

			for (String key : atts.keySet()) {
				Attribute attribute = atts.get(key);

				for (Measure m : attribute.getMeasures()) {
					// generate translation rules for measure

					String rule = ruleTmpl
							.replace(Constants.ABOUT_KEY, m.getId())
							.replace(Constants.NAME_KEY, m.getName())
							.replace(Constants.DESCRIPTION_KEY,
									m.getDescription())
							.replace(Constants.ATTRIBUTE_KEY, attribute.getId())
							.replace("@{attr_name}", attribute.getName())
							.replace("@{attr_description}",
									attribute.getDescription())
							.replace(
									Constants.CATEGORY_KEY,
									this.getIdFromString(attribute
											.getCategory().getName()))
							.replace(
									Constants.SCOPE_KEY,
									this.getScopeId(attribute.getCategory()
											.getScope()))
							.replace("@{categoryname}",
									attribute.getCategory().getName())
							.replace("@{measurement_uri}",
									m.getOldPropertyUri().trim())
							.replaceAll("@\\{scale\\}",
									this.getScaleId(m.getScale()));

					String restriction = m.getRestriction();
					if ("booleanScale".equals(this.getScaleId(m.getScale()))) {
						restriction = "Yes/No";
					}
					if (restriction  != null && !"".equals(restriction)) {
						rule = rule.replace(Constants.RESTRICTION_KEY, "restriction=\""+restriction+"\"");
					} else {
						rule = rule.replace(Constants.RESTRICTION_KEY, "");
					}
					
					if (m.getScale() != null && !m.getScale().equals("")) {
						if (m.getOldPropertyUri() != null
								&& !"".equals(m.getOldPropertyUri())) {
							rules.append(rule);
						} else {
							rules.append("<!-- no old property for measure /"
									+ m.getId() + " -->\n");
						}
					} else {
						rules.append("<!-- no scale for measure /" + m.getId()
								+ " -->\n");
					}
				}
			}

			return rules.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getIdFromString(String str) {
		return str.toLowerCase().replace(" ", "_").replace(":", "_")
				.replace("__", "_");
	}

	private String getScaleId(String scale) {
		if (scale == null || "".equals(scale)) {
			return "";
		}
		String s = scale.replace(" ", "");
		s = s.substring(0, 1).toLowerCase() + s.substring(1) + "Scale";
		if ("positiveNumberScale".equals(s)) {
			return "positiveFloatScale";
		} 
		if ("numberScale".equals(s)) {
			return "floatScale";
		} 
		return s;
	}

	private String getScopeId(String scope) {
		return scope.toUpperCase().replace(" ", "_");
	}

}
