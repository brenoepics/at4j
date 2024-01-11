package tech.brenoepic.at4j.data.request.optional;

/**
 * Specifies how profanities should be treated in translations.
 * <br>
 * Possible values are: <b>Marked</b>, <b>Deleted</b> or <b>NoAction</b> (default).
 * @see <a href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-translate#handle-profanity">Handle profanity</a>
 */
public enum ProfanityAction {
    MARKED("Marked"),
    DELETED("Deleted"),
    NO_ACTION("NoAction");

    private final String value;
    ProfanityAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
