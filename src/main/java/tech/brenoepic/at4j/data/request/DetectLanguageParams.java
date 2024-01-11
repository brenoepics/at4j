package tech.brenoepic.at4j.data.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DetectLanguageParams {

    private final String text;

    public DetectLanguageParams(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public ArrayNode getBody() {
        if (text == null || text.isEmpty()) return null;

        ObjectNode textNode = JsonNodeFactory.instance.objectNode();
        textNode.put("Text", text);
        return JsonNodeFactory.instance.arrayNode().add(textNode);

    }
}
