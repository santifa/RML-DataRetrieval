package be.ugent.mmlab.rml.input.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval: Template Processor
 *
 * @author andimou
 */
public class TemplateProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(TemplateProcessor.class);
    
    public String processTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            String expression = parameter.getKey();
            String replacement = parameter.getValue();

            log.error("Template processing...");
            if (expression.contains("[")) {
                expression = expression.replaceAll("\\[", "").replaceAll("\\]", "");
                template = template.replaceAll("\\[", "").replaceAll("\\]", "");
            }
            try {
                //TODO: replace the following with URIbuilder
                template = template.replaceAll(
                        "\\{" + Pattern.quote(expression) + "\\}",
                        URLEncoder.encode(replacement, "UTF-8")
                        .replaceAll("\\+", "%20")
                        .replaceAll("\\%21", "!")
                        .replaceAll("\\%27", "'")
                        .replaceAll("\\%28", "(")
                        .replaceAll("\\%29", ")")
                        .replaceAll("\\%7E", "~"));
            } catch (UnsupportedEncodingException ex) {
                log.error("UnsupportedEncodingException " + ex);
            }
        }
        return template.toString();
    }

}
