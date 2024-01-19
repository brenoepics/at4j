package io.github.brenoepics.at4j.data.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.brenoepics.at4j.azure.lang.Language;
import io.github.brenoepics.at4j.data.request.optional.ProfanityAction;
import io.github.brenoepics.at4j.data.request.optional.ProfanityMarker;
import io.github.brenoepics.at4j.data.request.optional.TextType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the parameters for a translation request. It includes options for handling
 * profanity, text type, alignment, sentence length, source language, target languages, and a
 * fallback language.
 */
public class TranslateParams {
  // The text to be translated
  private String text;
  // The type of the text to be translated (plain or HTML)
  private TextType textType;
  // The action to be taken on profanities in the text
  private ProfanityAction profanityAction;
  // The marker to be used for profanities in the text
  private ProfanityMarker profanityMarker;
  // Whether to include alignment in the translation
  private Boolean includeAlignment;
  // Whether to include sentence length in the translation
  private Boolean includeSentenceLength;
  // The source language of the text
  private String sourceLanguage;
  // The target languages for the translation
  private Collection<String> targetLanguages;
  // The suggested language if the source language can't be identified
  private String suggestedFromLanguage;

  /**
   * Constructor that initializes the text to be translated.
   *
   * @param text The text to be translated.
   * @param targetLanguages The target languages for the translation.
   */
  public TranslateParams(String text, Collection<String> targetLanguages) {
    this.text = text;
    this.targetLanguages = targetLanguages;
  }

  /**
   * Sets the text to be translated.
   *
   * @param text The text to be translated.
   * @return This instance.
   */
  public TranslateParams setText(String text) {
    this.text = text;
    return this;
  }

  /**
   * Defines whether the text being translated is plain text or HTML text. Any HTML needs to be a
   * well-formed, complete element.
   *
   * @param textType Possible values are: plain (default) or html.
   * @return This instance.
   */
  public TranslateParams setTextType(TextType textType) {
    this.textType = textType;
    return this;
  }

  /**
   * Specifies how profanities should be treated in translations.
   *
   * @param profanityAction Possible values are: Marked, Deleted or NoAction (default).
   * @return This instance.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Handle
   *     profanity</a>
   */
  public TranslateParams setProfanityAction(ProfanityAction profanityAction) {
    this.profanityAction = profanityAction;
    return this;
  }

  /**
   * Specifies how profanities should be marked in translations.
   *
   * @param profanityMarker Possible values are: Asterisk (default) or Tag. To understand ways to
   *     treat profanity.
   * @return This instance.
   * @see <a
   *     href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Profanity
   *     Handling</a>
   */
  public TranslateParams setProfanityMarker(ProfanityMarker profanityMarker) {
    this.profanityMarker = profanityMarker;
    return this;
  }

  /**
   * Specifies whether to include the alignment string in the response. The alignment string is a
   * string of alphanumeric pairs separated by spaces. Each pair indicates the start and end of the
   * corresponding word in the translated text.
   *
   * @param includeAlignment Specifies whether to include the alignment string in the response.
   * @return This instance.
   */
  public TranslateParams setIncludeAlignment(Boolean includeAlignment) {
    this.includeAlignment = includeAlignment;
    return this;
  }

  /**
   * Specifies whether to include the number of characters in each translated sentence.
   *
   * @param includeSentenceLength Specifies whether to include the number of characters in each
   *     translated sentence.
   * @return This instance.
   */
  public TranslateParams setIncludeSentenceLength(Boolean includeSentenceLength) {
    this.includeSentenceLength = includeSentenceLength;
    return this;
  }

  /**
   * Specifies a fallback language if the language of the input text can't be identified. <br>
   * Language autodetect is applied when the form parameter is omitted. If detection fails, the
   * `suggestedFrom` language is assumed
   *
   * @param suggestedFromLanguage A string representing the language code of the translation text.
   * @return This instance.
   */
  public TranslateParams setSuggestedFromLanguage(String suggestedFromLanguage) {
    this.suggestedFromLanguage = suggestedFromLanguage;
    return this;
  }

  /**
   * Sets the source language for the translation.
   *
   * @param sourceLanguage The source language for the translation.
   * @return This instance.
   */
  public TranslateParams setSourceLanguage(Language sourceLanguage) {
    this.sourceLanguage = sourceLanguage.getCode();
    return this;
  }

  /**
   * Sets the source language for the translation.
   *
   * @param sourceLanguage The source language for the translation.
   * @return This instance.
   */
  public TranslateParams setSourceLanguage(String sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
    return this;
  }

  /**
   * Sets the target languages for the translation.
   *
   * @param targetLanguages The target languages for the translation.
   * @return This instance.
   */
  public TranslateParams setTargetLanguages(Collection<Language> targetLanguages) {
    this.targetLanguages =
        Collections.unmodifiableCollection(
            targetLanguages.stream()
                .map(Language::getCode)
                .collect(Collectors.toCollection(ArrayList::new)));
    return this;
  }

  /**
   * Sets the target languages for the translation.
   *
   * @param targetLanguages The target languages for the translation.
   * @return This instance.
   */
  public TranslateParams setTargetLanguages(String... targetLanguages) {
    this.targetLanguages = List.of(targetLanguages);
    return this;
  }

  public String getText() {
    return text;
  }

  public Boolean getIncludeAlignment() {
    return includeAlignment;
  }

  public Boolean getIncludeSentenceLength() {
    return includeSentenceLength;
  }

  public TextType getTextType() {
    return textType;
  }

  public ProfanityAction getProfanityAction() {
    return profanityAction;
  }

  public ProfanityMarker getProfanityMarker() {
    return profanityMarker;
  }

  public String getSourceLanguage() {
    return sourceLanguage;
  }

  public Collection<String> getTargetLanguages() {
    return targetLanguages;
  }

  public String getSuggestedFromLanguage() {
    return suggestedFromLanguage;
  }

  /**
   * Returns the parameters as a map.
   *
   * @return A map of query parameters.
   */
  public Map<String, String> getQueryParameters() {
    Map<String, String> params = new HashMap<>();
    if (getSourceLanguage() != null) {
      params.put("from", getSourceLanguage());
    }

    if (getTextType() != null) {
      params.put("textType", getTextType().getValue());
    }

    if (getProfanityAction() != null) {
      params.put("profanityAction", getProfanityAction().getValue());
      if (getProfanityMarker() != null)
        params.put("profanityMarker", getProfanityMarker().getValue());
    }

    if (getIncludeAlignment() != null) {
      params.put("includeAlignment", getIncludeAlignment().toString());
    }

    if (getIncludeSentenceLength() != null) {
      params.put("includeSentenceLength", getIncludeSentenceLength().toString());
    }

    if (getSuggestedFromLanguage() != null) {
      params.put("suggestedFrom", getSuggestedFromLanguage());
    }

    return params;
  }

  /**
   * Returns the body of the request as a JSON node.
   *
   * @return The body of the request.
   */
  public JsonNode getBody() {
    ArrayNode body = JsonNodeFactory.instance.arrayNode();
    if (getText() != null && !getText().isEmpty()) {
      ObjectNode textNode = JsonNodeFactory.instance.objectNode();
      textNode.put("Text", getText());
      body.add(textNode);
    }
    return body;
  }
}
