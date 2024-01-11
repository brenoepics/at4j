package tech.brenoepic.at4j.data.response;

import tech.brenoepic.at4j.data.DetectedLanguage;
import tech.brenoepic.at4j.data.Translation;

import javax.annotation.Nullable;
import java.util.Collection;

public class TranslationResponse {

    @Nullable
    private DetectedLanguage detectedLanguage = null;
    private final Collection<Translation> translations;

    public TranslationResponse(@Nullable DetectedLanguage detectedLanguage, Collection<Translation> translations) {
        this.detectedLanguage = detectedLanguage;
        this.translations = translations;
    }

    public TranslationResponse(Collection<Translation> translations) {
        this.translations = translations;
    }


    public void setDetectedLanguage(@Nullable DetectedLanguage detectedLanguage) {
        this.detectedLanguage = detectedLanguage;
    }

    @Nullable
    public DetectedLanguage getDetectedLanguage() {
        return detectedLanguage;
    }
    public Collection<Translation> getTranslations() {
        return translations;
    }
}
