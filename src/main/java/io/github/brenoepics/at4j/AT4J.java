package io.github.brenoepics.at4j;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

/**
 * This class contains constants related to the AT4J version, commit ID, build timestamp, display
 * version, GitHub URL, user agent, and Azure Translator API version. The values of these constants
 * are loaded from a properties file at runtime.
 */
public class AT4J {
  /** The current AT4J version. This is loaded from the properties file at runtime. */
  public static final String VERSION;

  /**
   * The commit ID from which the current AT4J version was built. This is loaded from the properties
   * file at runtime.
   */
  public static final String COMMIT_ID;

  /**
   * The build timestamp at which the current AT4J version was built. This is loaded from the
   * properties file at runtime.
   */
  public static final Instant BUILD_TIMESTAMP;

  /**
   * The display version of the current AT4J version. If the current AT4J version is a release
   * version, it is equal to {@link #VERSION}. If the current AT4J version is a snapshot version, it
   * consists of the {@link #VERSION}, the {@link #COMMIT_ID}, and the {@link #BUILD_TIMESTAMP}. For
   * displaying the version somewhere, it is best to use this constant.
   */
  public static final String DISPLAY_VERSION;

  static {
    Properties versionProperties = new Properties();
    try (InputStream versionPropertiesStream =
        AT4J.class.getClassLoader().getResourceAsStream("git.properties")) {
      versionProperties.load(versionPropertiesStream);
    } catch (IOException ignored) {
      // ignored
    }

    VERSION = versionProperties.getProperty("version", "<unknown>");
    COMMIT_ID = versionProperties.getProperty("git.commit.id.abbrev", "<unknown>");

    String buildTimestamp = versionProperties.getProperty("buildTimestamp", null);
    BUILD_TIMESTAMP = buildTimestamp == null ? null : Instant.parse(buildTimestamp);

    DISPLAY_VERSION =
        VERSION.endsWith("-SNAPSHOT")
            ? String.format("%s [%s]", VERSION, BUILD_TIMESTAMP)
            : VERSION;
  }

  /** The GitHub url of AT4J. */
  public static final String GITHUB_URL = "https://github.com/brenoepics/at4j";

  /**
   * The user agent used for requests. It is composed of the GitHub URL and the display version of
   * AT4J.
   */
  public static final String USER_AGENT = "AT4J (" + GITHUB_URL + ", v" + DISPLAY_VERSION + ")";

  /**
   * The API version from Azure Translator which we are using. The reference can be found <a
   * href="https://learn.microsoft.com/en-us/azure/ai-services/translator/reference/v3-0-reference">here</a>.
   */
  public static final String AZURE_TRANSLATOR_API_VERSION = "3.0";

  /** Private constructor to prevent instantiation of this utility class. */
  private AT4J() {
    throw new UnsupportedOperationException();
  }
}
